package de.hypercdn.ticat.server.helper.accesscontrol.user

import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.helper.accesscontrol.AccessRequest
import de.hypercdn.ticat.server.helper.accesscontrol.AccessorContainer
import de.hypercdn.ticat.server.helper.accesscontrol.EntityAccessController
import de.hypercdn.ticat.server.helper.accesscontrol.user.rules.UpdateUserDetails
import de.hypercdn.ticat.server.helper.accesscontrol.user.rules.UpdateUserPermissions
import de.hypercdn.ticat.server.helper.accesscontrol.user.rules.ViewUserExtendedRule
import de.hypercdn.ticat.server.helper.accesscontrol.user.rules.ViewUserRule

enum class UserExecutableAction : AccessRequest {
    VIEW_USER,              // view general details
    VIEW_USER_EXTENDED,     // view detailed user details
    UPDATE_USER_DETAILS,    // update user details
    UPDATE_PERMISSIONS      // update user permissions
}

class UserAccessor(val user: User?) : AccessorContainer {
    override fun isEmpty(): Boolean {
        return user == null
    }
}

class UserAccessController : EntityAccessController<User, UserExecutableAction, UserAccessor>() {
    override val accessRules = listOf(
        ViewUserRule(),
        ViewUserExtendedRule(),
        UpdateUserDetails(),
        UpdateUserPermissions()
    )
}