package de.hypercdn.ticat.server.helper.accesscontrol.user.rules

import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.helper.accesscontrol.AccessRule
import de.hypercdn.ticat.server.helper.accesscontrol.AccessRuleContext
import de.hypercdn.ticat.server.helper.accesscontrol.user.UserAccessor
import de.hypercdn.ticat.server.helper.accesscontrol.user.UserExecutableAction

class UpdateUserPermissions : AccessRule<User, UserExecutableAction, UserAccessor>() {
    override fun isApplicableForRequest(request: UserExecutableAction): Boolean {
        return request == UserExecutableAction.UPDATE_PERMISSIONS
    }

    override fun definition(): AccessRuleContext<User, UserExecutableAction, UserAccessor>.() -> Unit = {
        exitWithFailureIfFalse("Accessor has to been defined") {
            input?.accessor?.isEmpty() != false
        }
        exitWithFailureIfFalse("Target user must be defined") {
            input?.entity != null
        }
        exitWithFailureIfFalse("User is of AccountType.ADMIN") {
            input?.accessor?.user?.accountType == User.AccountType.ADMIN
        }
    }

}