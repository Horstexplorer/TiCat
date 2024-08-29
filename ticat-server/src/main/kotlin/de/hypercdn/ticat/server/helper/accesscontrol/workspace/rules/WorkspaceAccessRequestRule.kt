package de.hypercdn.ticat.server.helper.accesscontrol.workspace.rules

import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.user.effectivePermissions
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import de.hypercdn.ticat.server.helper.accesscontrol.AccessRule
import de.hypercdn.ticat.server.helper.accesscontrol.workspace.WorkspaceAccessor
import de.hypercdn.ticat.server.helper.accesscontrol.workspace.WorkspaceExecutableAction

class WorkspaceAccessRequestRule : AccessRule<Workspace, WorkspaceExecutableAction, WorkspaceAccessor> {
    override fun isApplicableForRequest(request: WorkspaceExecutableAction): Boolean {
        return WorkspaceExecutableAction.ACCESS_REQUEST == request
    }

    override fun testGrant(
        instance: Workspace?,
        request: WorkspaceExecutableAction,
        accessorContainer: WorkspaceAccessor
    ): Boolean {
        if (instance == null)
            return false
        if (accessorContainer.isEmpty())
            return false
        if (accessorContainer.user == null)
            return false
        if (accessorContainer.member != null)
            return false
        if (accessorContainer.user.accountType == User.AccountType.ADMIN)
            return true
        if (instance.settings.accessMode == Workspace.Settings.AccessMode.INVITE_ONLY)
            return false
        val effectivePermission = accessorContainer.user.effectivePermissions()
        if (effectivePermission.canRequestWorkspaceAccess)
            return true
        return false
    }

}