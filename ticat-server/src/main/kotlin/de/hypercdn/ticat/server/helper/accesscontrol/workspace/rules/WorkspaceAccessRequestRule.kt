package de.hypercdn.ticat.server.helper.accesscontrol.workspace.rules

import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.user.effectivePermissions
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import de.hypercdn.ticat.server.helper.accesscontrol.AccessRule
import de.hypercdn.ticat.server.helper.accesscontrol.AccessRuleContext
import de.hypercdn.ticat.server.helper.accesscontrol.workspace.WorkspaceAccessor
import de.hypercdn.ticat.server.helper.accesscontrol.workspace.WorkspaceExecutableAction

class WorkspaceAccessRequestRule : AccessRule<Workspace, WorkspaceExecutableAction, WorkspaceAccessor>() {
    override fun isApplicableForRequest(request: WorkspaceExecutableAction): Boolean {
        return WorkspaceExecutableAction.ACCESS_REQUEST == request
    }

    override fun definition(): AccessRuleContext<Workspace, WorkspaceExecutableAction, WorkspaceAccessor>.() -> Unit = {
        exitWithFailureIfFalse("Accessor has to been defined") {
            input?.accessor?.isEmpty() != false
        }
        exitWithFailureIfFalse("Workspace must be defined") {
            input?.entity != null
        }
        exitWithFailureIfFalse("Member must be undefined") {
            input?.accessor?.member == null
        }
        exitWithSuccessIfTrue("User is of AccountType.ADMIN") {
            input?.accessor?.user?.accountType == User.AccountType.ADMIN
        }
        exitWithFailureIfFalse("Workspace accepts join requests") {
            input?.entity?.settings?.accessMode != Workspace.Settings.AccessMode.INVITE_ONLY
        }
        exitWithSuccessIfTrue("User has permission to request access to workspace") {
            val effectivePermission = input?.accessor?.user?.effectivePermissions()
            effectivePermission?.canRequestWorkspaceAccess == true
        }
    }

}