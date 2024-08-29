package de.hypercdn.ticat.server.helper.accesscontrol.workspace.rules

import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import de.hypercdn.ticat.server.data.sql.entities.workspace.isDeleted
import de.hypercdn.ticat.server.data.sql.entities.workspace.member.WorkspaceMember
import de.hypercdn.ticat.server.data.sql.entities.workspace.member.effectivePermission
import de.hypercdn.ticat.server.helper.accesscontrol.AccessRule
import de.hypercdn.ticat.server.helper.accesscontrol.workspace.WorkspaceAccessor
import de.hypercdn.ticat.server.helper.accesscontrol.workspace.WorkspaceExecutableAction

class ViewWorkspaceRule : AccessRule<Workspace, WorkspaceExecutableAction, WorkspaceAccessor> {
    override fun isApplicableForRequest(request: WorkspaceExecutableAction): Boolean {
        return WorkspaceExecutableAction.VIEW_WORKSPACE == request
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
        if (accessorContainer.user?.accountType == User.AccountType.ADMIN)
            return true
        if (instance.isDeleted())
            return false
        if (accessorContainer.user?.uuid == instance.creatorUUID)
            return true
        if (accessorContainer.member?.userUUID == instance.creatorUUID)
            return true
        if (accessorContainer.member == null)
            return false
        val effectivePermission = accessorContainer.member.effectivePermission()
        if (effectivePermission.hasWorkspacePermissionOrHigher(WorkspaceMember.Permissions.WorkspacePermission.CAN_VIEW))
            return true
        return false
    }

}