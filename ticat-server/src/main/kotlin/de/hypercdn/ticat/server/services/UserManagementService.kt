package de.hypercdn.ticat.server.services

import de.hypercdn.ticat.server.config.auth.JwtConfig
import de.hypercdn.ticat.server.data.sql.entities.user.GUEST_UUID
import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.user.UserRepository
import de.hypercdn.ticat.server.data.sql.entities.user.authcache.UserAuthCacheRepository
import de.hypercdn.ticat.server.data.sql.entities.user.authcache.UserAuthCacheReference
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import lombok.extern.slf4j.Slf4j
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.vault.support.JsonMapFlattener
import org.springframework.web.server.ResponseStatusException
import java.lang.IllegalStateException
import java.time.ZoneOffset
import java.util.*

@Slf4j
@Service
class UserManagementService(
    private val userRepository: UserRepository,
    private val userAuthCacheRepository: UserAuthCacheRepository,
    private val jwtConfig: JwtConfig
) {

    private val logger: KLogger = KotlinLogging.logger{}

    fun currentContextIsAuthenticated(): Boolean {
        val authentication = SecurityContextHolder
            .getContext().authentication
        return authentication != null && authentication.isAuthenticated
    }

    fun currentContextJwtAuthenticationToken(): JwtAuthenticationToken {
        val authentication = SecurityContextHolder
            .getContext().authentication

        if (authentication is JwtAuthenticationToken)
            return authentication
        throw IllegalStateException("No jwt authentication available or type mismatch: Authentication is of type ${authentication::class.simpleName}")
    }

    private fun userEntityFromCurrentContext(): User {
        val jwt = currentContextJwtAuthenticationToken()
        val flatJwtClaims = JsonMapFlattener.flatten(jwt.token.claims)
        logger.debug{"Creating new user entity from current auth context (asr:${jwt.token.subject})"}

        return User()
            .apply {
                authSubjectReference = jwt.token.subject

                (flatJwtClaims[jwtConfig.propertyMapping.firstName] as? String)?.let { name.firstName = it }
                (flatJwtClaims[jwtConfig.propertyMapping.familyName] as? String)?.let { name.lastName = it }
                (flatJwtClaims[jwtConfig.propertyMapping.displayName] as? String)?.let { name.displayName = it }

                (flatJwtClaims[jwtConfig.propertyMapping.email] as? String)?.let { email = it }

                (flatJwtClaims[jwtConfig.propertyMapping.roles] as? List<*>)?.let {
                    if (it.contains(jwtConfig.roleMapping.accountEnable))
                        settings.status = User.Settings.Status.ACTIVE
                    else
                        settings.status = User.Settings.Status.DISABLED
                    if (it.contains(jwtConfig.roleMapping.accountTypeAdmin))
                        accountType = User.AccountType.ADMIN
                }
            }
    }

    private fun userAuthCacheReferenceFromCurrentContext(): UserAuthCacheReference {
        val jwt = currentContextJwtAuthenticationToken()
        logger.debug{"Creating new auth reference entity from current auth context (asr:${jwt.token.subject} aid:${jwt.token.id})"}

        return UserAuthCacheReference()
            .apply {
                authSubjectReference = jwt.token.subject
                authIdentifier = jwt.token.id
                jwt.token.expiresAt?.let { predictedExpiry = it.atOffset(ZoneOffset.UTC) }
            }
    }

    private fun currentContextJwtIsAlreadyKnown(): Boolean {
        return userAuthCacheRepository.existsByAuthSubjectReferenceAndAuthIdentifier(
            currentContextJwtAuthenticationToken().token.subject,
            currentContextJwtAuthenticationToken().token.id
        )
    }

    fun userForCurrentContext() = userForCurrentContextOrByFallback(null)

    fun userForCurrentContextOrByFallback(fallback: UUID? = GUEST_UUID): User? = when {
        currentContextIsAuthenticated() -> {
            if (currentContextJwtIsAlreadyKnown()) {
                logger.debug{"Context is authenticated and jwt already known"}
                userRepository.getUserByAuthSubjectReference(currentContextJwtAuthenticationToken().token.subject)
            } else {
                logger.debug{"Context is authenticated but jwt is not yet known - will update entity with new contents"}
                userAuthCacheRepository.save(userAuthCacheReferenceFromCurrentContext())
                userRepository.save(userEntityFromCurrentContext()) // todo: trigger user change (jwt) event
            }
        }

        fallback != null -> {
            userRepository.findByIdOrNull(fallback)
        }

        else -> null
    }

    fun activeUserForCurrentContextElseThrow(exception: Throwable = ResponseStatusException(HttpStatus.FORBIDDEN)): User = activeUserForCurrentContextOrByFallbackElseThrow(null)

    fun activeUserForCurrentContextOrByFallbackElseThrow(
        fallback: UUID? = GUEST_UUID,
        exception: Throwable = ResponseStatusException(HttpStatus.FORBIDDEN)
    ): User {
        val user = userForCurrentContextOrByFallback(fallback)
        if (user == null || user.settings.status != User.Settings.Status.ACTIVE) {
            logger.warn{"Failed to get active user for authentication: (authenticated: ${currentContextIsAuthenticated()}, user-status: ${user?.settings?.status}, fallback-allowed:${fallback != null})"}
            throw exception
        }
        return user
    }

    fun lookupUserByUUID(uuid: UUID): User? = userRepository.findByIdOrNull(uuid)

}