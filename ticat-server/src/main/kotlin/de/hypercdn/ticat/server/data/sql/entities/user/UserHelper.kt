package de.hypercdn.ticat.server.data.sql.entities.user

import java.util.*


val SYSTEM_UUID: UUID = UUID.fromString("00000000-0000-4000-0000-000000000000")
val GUEST_UUID: UUID = UUID.fromString("00000000-0000-4000-0000-000000000002")


class EffectivePermissions(user: User) : User.Permissions() {
    init {
        canCreateNewWorkspaces = user.accountType == User.AccountType.ADMIN || user.permissions.canCreateNewWorkspaces
        canRequestWorkspaceAccess = user.accountType == User.AccountType.ADMIN || user.permissions.canRequestWorkspaceAccess
        canSendMessagesToUsers = user.accountType == User.AccountType.ADMIN || user.permissions.canSendMessagesToUsers
    }
}

fun User.effectivePermissions(): EffectivePermissions {
    return EffectivePermissions(this)
}