package de.hypercdn.ticat.server.data.sql.entities.workspace.member

import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace

class EffectivePermission(user: User?, workspace: Workspace?, member: WorkspaceMember?): WorkspaceMember.Permissions() {
    init {
        pagePermission = when {
            user != null && user.accountType == User.AccountType.ADMIN -> PagePermission.CAN_VIEW_CREATE_EDIT_DELETE
            workspace != null && workspace.creatorUUID == user?.uuid -> PagePermission.CAN_VIEW_CREATE_EDIT_DELETE
            member != null && member.membershipStatus == WorkspaceMember.MembershipStatus.MEMBERSHIP_GRANTED -> member.permissions.pagePermission
            else -> PagePermission.DENY
        }
        boardPermission = when {
            user != null && user.accountType == User.AccountType.ADMIN -> BoardPermission.CAN_VIEW_CREATE_EDIT_DELETE
            workspace != null && workspace.creatorUUID == user?.uuid -> BoardPermission.CAN_VIEW_CREATE_EDIT_DELETE
            member != null && member.membershipStatus == WorkspaceMember.MembershipStatus.MEMBERSHIP_GRANTED -> member.permissions.boardPermission
            else -> BoardPermission.DENY
        }
        ticketPermission = when {
            user != null && user.accountType == User.AccountType.ADMIN -> TicketPermission.CAN_VIEW_CREATE_EDIT_DELETE
            workspace != null && workspace.creatorUUID == user?.uuid -> TicketPermission.CAN_VIEW_CREATE_EDIT_DELETE
            member != null && member.membershipStatus == WorkspaceMember.MembershipStatus.MEMBERSHIP_GRANTED -> member.permissions.ticketPermission
            else -> TicketPermission.DENY
        }
        workspacePermission = when {
            user != null && user.accountType == User.AccountType.ADMIN -> WorkspacePermission.CAN_ADMINISTRATE
            workspace != null && workspace.creatorUUID == user?.uuid -> WorkspacePermission.CAN_ADMINISTRATE
            member != null && member.membershipStatus == WorkspaceMember.MembershipStatus.MEMBERSHIP_GRANTED -> member.permissions.workspacePermission
            else -> WorkspacePermission.DENY
        }
    }

    fun hasPagePermissionOrHigher(requested: PagePermission): Boolean = requested.value <= pagePermission.value
    fun hasBoardPermissionOrHigher(requested: BoardPermission): Boolean = requested.value <= boardPermission.value
    fun hasTicketPermissionOrHigher(requested: TicketPermission): Boolean = requested.value <= ticketPermission.value
    fun hasWorkspacePermissionOrHigher(requested: WorkspacePermission): Boolean = requested.value <= workspacePermission.value
}

fun WorkspaceMember.effectivePermission() = EffectivePermission(this.user, this.workspace, this)
fun WorkspaceMember.effectivePermission(user: User? = null, workspace: Workspace? = null) = EffectivePermission(user, workspace, this)

