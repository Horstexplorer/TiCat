package de.hypercdn.ticat.server.helper.accesscontrol.workspace.rules

import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.user.effectivePermissions
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import de.hypercdn.ticat.server.helper.accesscontrol.AccessRule
import de.hypercdn.ticat.server.helper.accesscontrol.AccessRuleContext
import de.hypercdn.ticat.server.helper.accesscontrol.workspace.WorkspaceAccessor
import de.hypercdn.ticat.server.helper.accesscontrol.workspace.WorkspaceExecutableAction

class CreateWorkspaceRule : AccessRule<Workspace, WorkspaceExecutableAction, WorkspaceAccessor>() {

    override fun isApplicableForRequest(request: WorkspaceExecutableAction): Boolean = WorkspaceExecutableAction.CREATE_WORKSPACE == request

    override fun definition(): AccessRuleContext<Workspace, WorkspaceExecutableAction, WorkspaceAccessor>.() -> Unit = {
        exitWithFailureIfFalse("Accessor has to been defined") {
            input?.accessor?.isEmpty() != false
        }
        exitWithFailureIfTrue("Workspace cannot be defined") {
            input?.entity == null
        }
        exitWithSuccessIfTrue("User is of AccountType.ADMIN") {
            input?.accessor?.user?.accountType == User.AccountType.ADMIN
        }
        exitWithSuccessIfTrue("User has permission to create workspace") {
            val effectivePermission = input?.accessor?.user?.effectivePermissions()
            effectivePermission?.canCreateNewWorkspaces == true
        }
    }

}