package de.hypercdn.ticat.server.helper.accesscontrol.user.rules

import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.helper.accesscontrol.AccessRule
import de.hypercdn.ticat.server.helper.accesscontrol.AccessRuleContext
import de.hypercdn.ticat.server.helper.accesscontrol.user.UserAccessor
import de.hypercdn.ticat.server.helper.accesscontrol.user.UserExecutableAction

class UpdateUserDetails : AccessRule<User, UserExecutableAction, UserAccessor>() {
    override fun isApplicableForRequest(request: UserExecutableAction): Boolean {
        return request == UserExecutableAction.UPDATE_USER_DETAILS
    }

    override fun definition(): AccessRuleContext<User, UserExecutableAction, UserAccessor>.() -> Unit = {
        exitWithFailureIfFalse("Accessor has to been defined") {
            input?.accessor?.isEmpty() != false
        }
        exitWithFailureIfFalse("Target user must be defined") {
            input?.entity != null
        }
        exitWithSuccessIfTrue("User is of AccountType.ADMIN") {
            input?.accessor?.user?.accountType == User.AccountType.ADMIN
        }
        exitWithFailureIfFalse("User must be DEFAULT") {
            input?.accessor?.user?.accountType == User.AccountType.DEFAULT
        }
        exitWithFailureIfFalse("User is matching self") {
            input?.accessor?.user?.uuid == input?.entity?.uuid
        }
    }

}