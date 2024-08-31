package de.hypercdn.ticat.server.data.json.entities.user

import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.user.effectivePermissions
import de.hypercdn.ticat.server.helper.EntityTemplateBuilder

class UserResponseJsonBuilder(
    private val user: User? = null
): EntityTemplateBuilder<UserResponseJsonBuilder, UserResponseJson>({ UserResponseJson() }) {

    fun includeId(skip: Boolean = false): UserResponseJsonBuilder = modify(skip) {
        it.uuid = user?.uuid
    }

    fun includeVersionTimestamp(skip: Boolean = false): UserResponseJsonBuilder = modify(skip) {
        it.versionTimestamp = user?.modifiedAt
    }

    enum class NameRule {
        DISPLAY_NAME,
        REAL_NAME,
        FULL_NAME
    }

    fun includeName(rule: NameRule, skip: Boolean = false): UserResponseJsonBuilder = modify(skip) {
        val name = UserResponseJson.Name().apply {
            when (rule) {
                NameRule.DISPLAY_NAME -> {
                    displayName = user?.name?.displayName
                }
                NameRule.REAL_NAME -> {
                    firstName = user?.name?.firstName
                    lastName = user?.name?.lastName
                }
                NameRule.FULL_NAME -> {
                    displayName = user?.name?.displayName
                    firstName = user?.name?.firstName
                    lastName = user?.name?.lastName
                }
            }
        }
        it.name = name
    }

    fun includeEmail(skip: Boolean = false): UserResponseJsonBuilder = modify(skip) {
        it.email = user?.email
    }

    fun includeAccountType(skip: Boolean = false): UserResponseJsonBuilder = modify(skip) {
        it.accountType = user?.accountType
    }

    enum class PermissionRule {
        SET_PERMISSIONS,
        EFFECTIVE_PERMISSIONS,
        ALL_PERMISSIONS
    }

    fun includePermissions(rule: PermissionRule, skip: Boolean = false): UserResponseJsonBuilder = modify(skip) {
        val setPermissions = UserResponseJson.Permissions().apply {
            canCreateNewWorkspaces = user?.permissions?.canCreateNewWorkspaces
            canRequestWorkspaceAccess = user?.permissions?.canRequestWorkspaceAccess
            canSendMessagesToUsers = user?.permissions?.canSendMessagesToUsers
        }
        val effectivePermission = user?.effectivePermissions()
        val effectivePermissions = UserResponseJson.Permissions().apply {
            canCreateNewWorkspaces = effectivePermission?.canCreateNewWorkspaces
            canRequestWorkspaceAccess = effectivePermission?.canRequestWorkspaceAccess
            canSendMessagesToUsers = effectivePermission?.canSendMessagesToUsers
        }
        when (rule) {
            PermissionRule.SET_PERMISSIONS -> {
                it.permission = setPermissions
            }
            PermissionRule.EFFECTIVE_PERMISSIONS -> {
                it.effectivePermission = effectivePermissions
            }
            PermissionRule.ALL_PERMISSIONS -> {
                it.permission = setPermissions
                it.effectivePermission = effectivePermissions
            }
        }
    }

    fun includeSettings(skip: Boolean = false): UserResponseJsonBuilder = modify(skip) {
        it.settings = UserResponseJson.Settings().apply {
            receiveWorkspaceInvitationsFromOrigin = user?.settings?.receiveWorkspaceInvitationsFromOrigin
            receiveMessagesFromOrigin = user?.settings?.receiveMessagesFromOrigin
            accountVisibility = user?.settings?.accountVisibility
            status = user?.settings?.status
            locale = user?.settings?.locale
        }
    }

}

fun UserResponseJson.Companion.builder(user: User? = null): UserResponseJsonBuilder = UserResponseJsonBuilder(user)
fun User.toJsonResponseBuilder(): UserResponseJsonBuilder = UserResponseJsonBuilder(this)