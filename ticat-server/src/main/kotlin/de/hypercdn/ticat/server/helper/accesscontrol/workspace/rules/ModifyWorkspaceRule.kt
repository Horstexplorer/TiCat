package de.hypercdn.ticat.server.helper.accesscontrol.workspace.rules

import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import de.hypercdn.ticat.server.data.sql.entities.workspace.isArchived
import de.hypercdn.ticat.server.data.sql.entities.workspace.isDeleted
import de.hypercdn.ticat.server.data.sql.entities.workspace.member.WorkspaceMember
import de.hypercdn.ticat.server.data.sql.entities.workspace.member.effectivePermission
import de.hypercdn.ticat.server.helper.accesscontrol.AccessRule
import de.hypercdn.ticat.server.helper.accesscontrol.AccessRuleContext
import de.hypercdn.ticat.server.helper.accesscontrol.workspace.WorkspaceAccessor
import de.hypercdn.ticat.server.helper.accesscontrol.workspace.WorkspaceExecutableAction

class ModifyWorkspaceRule : AccessRule<Workspace, WorkspaceExecutableAction, WorkspaceAccessor>() {
    override fun isApplicableForRequest(request: WorkspaceExecutableAction): Boolean {
        return WorkspaceExecutableAction.MODIFY_WORKSPACE == request
    }

    override fun definition(): AccessRuleContext<Workspace, WorkspaceExecutableAction, WorkspaceAccessor>.() -> Unit = {
        exitWithFailureIfFalse("Accessor has to been defined") {
            input?.accessor?.isEmpty() != false
        }
        exitWithFailureIfFalse("Workspace must be defined") {
            input?.entity != null
        }
        exitWithSuccessIfTrue("User is of AccountType.ADMIN") {
            input?.accessor?.user?.accountType == User.AccountType.ADMIN
        }
        exitWithFailureIfTrue("Workspace has been archived or deleted") {
            input?.entity?.isArchived() == true
                    || input?.entity?.isDeleted() == true
        }
        exitWithSuccessIfTrue("User / Member created this workspace") {
            (input?.accessor?.user != null
                    && input.accessor.user.uuid == input.entity?.creatorUUID)
                    || (input?.accessor?.member != null
                    && input.accessor.member.userUUID == input.entity?.creatorUUID)
        }
        exitWithSuccessIfTrue("Member has permission to edit this workspace") {
            val effectivePermission = input?.accessor?.member?.effectivePermission()
            effectivePermission?.hasWorkspacePermissionOrHigher(WorkspaceMember.Permissions.WorkspacePermission.CAN_VIEW_EDIT) == true
        }
    }

}