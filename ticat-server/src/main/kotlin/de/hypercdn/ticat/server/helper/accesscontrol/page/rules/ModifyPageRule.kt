package de.hypercdn.ticat.server.helper.accesscontrol.page.rules

import de.hypercdn.ticat.server.data.sql.entities.page.Page
import de.hypercdn.ticat.server.data.sql.entities.page.isArchived
import de.hypercdn.ticat.server.data.sql.entities.page.isDeleted
import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.workspace.isArchived
import de.hypercdn.ticat.server.data.sql.entities.workspace.isDeleted
import de.hypercdn.ticat.server.data.sql.entities.workspace.member.WorkspaceMember
import de.hypercdn.ticat.server.data.sql.entities.workspace.member.effectivePermission
import de.hypercdn.ticat.server.helper.accesscontrol.AccessRule
import de.hypercdn.ticat.server.helper.accesscontrol.page.PageExecutableAction
import de.hypercdn.ticat.server.helper.accesscontrol.workspace.WorkspaceScopedAccessor

class ModifyPageRule : AccessRule<Page, PageExecutableAction, WorkspaceScopedAccessor> {
    override fun isApplicableForRequest(request: PageExecutableAction): Boolean {
        return PageExecutableAction.MODIFY_PAGE == request
    }

    override fun testGrant(
        instance: Page?,
        request: PageExecutableAction,
        accessorContainer: WorkspaceScopedAccessor
    ): Boolean {
        if (instance == null)
            return false
        if (accessorContainer.isEmpty())
            return false
        if (accessorContainer.workspace == null)
            return false
        val workspace = accessorContainer.workspace
        if (accessorContainer.user?.accountType == User.AccountType.ADMIN)
            return true
        if (workspace.isDeleted())
            return false
        if (workspace.isArchived())
            return false
        if (instance.isDeleted())
            return false
        if (instance.isArchived())
            return false
        if (accessorContainer.user?.uuid == workspace.creatorUUID)
            return true
        if (accessorContainer.member?.uuid == workspace.creatorUUID)
            return true
        if (accessorContainer.member == null)
            return false
        val effectivePermission = accessorContainer.member.effectivePermission()
        if (effectivePermission.hasWorkspacePermissionOrHigher(WorkspaceMember.Permissions.WorkspacePermission.CAN_ADMINISTRATE))
            return true
        if (effectivePermission.hasPagePermissionOrHigher(WorkspaceMember.Permissions.PagePermission.CAN_VIEW_CREATE_EDIT))
            return true
        if (accessorContainer.member.uuid == instance.creatorUUID
            && effectivePermission.hasPagePermissionOrHigher(WorkspaceMember.Permissions.PagePermission.CAN_VIEW_CREATE))
            return true
        return false
    }
}