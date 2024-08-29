package de.hypercdn.ticat.server.helper.accesscontrol.workspace.rules

import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.user.effectivePermissions
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import de.hypercdn.ticat.server.helper.accesscontrol.AccessRule
import de.hypercdn.ticat.server.helper.accesscontrol.workspace.WorkspaceAccessor
import de.hypercdn.ticat.server.helper.accesscontrol.workspace.WorkspaceExecutableAction

class CreateWorkspaceRule : AccessRule<Workspace, WorkspaceExecutableAction, WorkspaceAccessor> {
    override fun isApplicableForRequest(request: WorkspaceExecutableAction): Boolean {
        return WorkspaceExecutableAction.CREATE_WORKSPACE == request
    }

    override fun testGrant(
        instance: Workspace?,
        request: WorkspaceExecutableAction,
        accessorContainer: WorkspaceAccessor
    ): Boolean {
        if (instance != null)
            return false
        if (accessorContainer.isEmpty())
            return false
        if (accessorContainer.user == null)
            return false
        if (accessorContainer.user.accountType == User.AccountType.ADMIN)
            return true
        val effectivePermission = accessorContainer.user.effectivePermissions()
        if (effectivePermission.canCreateNewWorkspaces)
            return true
        return false
    }

}