package de.hypercdn.ticat.server.helper.accesscontrol.user.rules

import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.helper.accesscontrol.AccessRule
import de.hypercdn.ticat.server.helper.accesscontrol.AccessRuleContext
import de.hypercdn.ticat.server.helper.accesscontrol.user.UserAccessor
import de.hypercdn.ticat.server.helper.accesscontrol.user.UserExecutableAction

class ViewUserExtendedRule : AccessRule<User, UserExecutableAction, UserAccessor>() {
    override fun isApplicableForRequest(request: UserExecutableAction): Boolean {
        return request == UserExecutableAction.VIEW_USER_EXTENDED
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
        exitWithSuccessIfTrue("User is matching self") {
            input?.accessor?.user?.uuid == input?.entity?.uuid
        }
        exitWithFailureIfTrue("User has been disabled") {
            input?.entity?.settings?.status == User.Settings.Status.ACTIVE
        }
        exitWithFailureIfFalse("User is not of AccountType.GUEST") {
            input?.accessor?.user?.accountType != User.AccountType.GUEST
        }
        exitWithSuccessIfTrue("Visible by LOGGED_IN_USER") {
            input?.entity?.settings?.accountVisibility == User.Settings.AccountVisibility.LOGGED_IN_USER
                    && input.accessor.user?.accountType == User.AccountType.DEFAULT
        }
        exitWithSuccessIfTrue("Visible to ANYONE") {
            input?.entity?.settings?.accountVisibility == User.Settings.AccountVisibility.ANYONE
        }
    }

}