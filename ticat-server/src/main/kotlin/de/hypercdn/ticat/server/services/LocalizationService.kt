package de.hypercdn.ticat.server.services

import de.hypercdn.ticat.server.config.LocalizationConfig
import de.hypercdn.ticat.server.config.getMessage
import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import de.hypercdn.ticat.server.helper.ExtendedReloadableResourceBundleMessageSource
import de.hypercdn.ticat.server.helper.asLanguageLocale
import mu.two.KLogger
import mu.two.KotlinLogging
import org.springframework.context.NoSuchMessageException
import org.springframework.stereotype.Component
import java.util.*

@Component
class LocalizationService(
    val localizationConfig: LocalizationConfig,
    val messageSource: ExtendedReloadableResourceBundleMessageSource
) {

    private val logger: KLogger = KotlinLogging.logger{}

    fun localeFromEntityContext(user: User): Locale = localeFromEntityContext(null, user)

    fun localeFromEntityContext(workspace: Workspace): Locale = localeFromEntityContext(workspace, null)

    fun localeFromEntityContext(workspace: Workspace? = null, user: User? = null): Locale {
        val workspaceLocale = workspace?.settings?.locale
        val userLocale = user?.settings?.locale
        val defaultLocale = localizationConfig.defaultLanguage
        if (workspace != null && workspaceLocale != null && isValidLocale(workspaceLocale) && localizationConfig.allowWorkspaceSpecificLocalizationOverride) {
            logger.debug{"Resolving locale from entity context using workspace locale $workspaceLocale"}
            return workspaceLocale
        }
        if (user != null && userLocale != null && isValidLocale(userLocale) && localizationConfig.allowUserSpecificLocalization) {
            logger.debug{"Resolving locale from entity context using user locale $userLocale"}
            return userLocale
        }
        logger.debug{"Resolving locale from entity context using fallback locale $defaultLocale"}
        return defaultLocale
    }

    fun isValidLocale(locale: Locale): Boolean {
        val language = locale.asLanguageLocale()
        val result = messageSource.containsLanguageFor(language)
        logger.debug{"Validating locale $locale as $language: $result"}
        return result
    }

    fun resolve(key: String, locale: Locale): String {
        val language = locale.asLanguageLocale()
        logger.debug{"Resolving $key with locale $locale as $language"}
        val message = try { messageSource.getMessage(key, locale) }
            catch (_: NoSuchMessageException) { key }
        if (message == key)
            logger.error{"Failed to resolve $key for locale $locale as $language"}
        return message
    }

}