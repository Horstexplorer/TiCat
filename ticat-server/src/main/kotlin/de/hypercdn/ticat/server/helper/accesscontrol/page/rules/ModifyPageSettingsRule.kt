package de.hypercdn.ticat.server.helper.accesscontrol.page.rules

import de.hypercdn.ticat.server.data.sql.entities.page.Page
import de.hypercdn.ticat.server.data.sql.entities.page.isDeleted
import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.workspace.isArchived
import de.hypercdn.ticat.server.data.sql.entities.workspace.isDeleted
import de.hypercdn.ticat.server.data.sql.entities.workspace.member.WorkspaceMember
import de.hypercdn.ticat.server.data.sql.entities.workspace.member.effectivePermission
import de.hypercdn.ticat.server.helper.accesscontrol.AccessRule
import de.hypercdn.ticat.server.helper.accesscontrol.AccessRuleContext
import de.hypercdn.ticat.server.helper.accesscontrol.page.PageExecutableAction
import de.hypercdn.ticat.server.helper.accesscontrol.workspace.WorkspaceScopedAccessor

class ModifyPageSettingsRule : AccessRule<Page, PageExecutableAction, WorkspaceScopedAccessor>() {
    override fun isApplicableForRequest(request: PageExecutableAction): Boolean {
        return PageExecutableAction.MODIFY_SETTINGS == request
    }

    override fun definition(): AccessRuleContext<Page, PageExecutableAction, WorkspaceScopedAccessor>.() -> Unit = {
        exitWithFailureIfFalse("Accessor has to been defined") {
            input?.accessor?.isEmpty() != false
        }
        exitWithFailureIfFalse("Workspace must be defined") {
            input?.accessor?.workspace != null
        }
        exitWithFailureIfTrue("Page must be defined") {
            input?.entity != null
        }
        exitWithSuccessIfTrue("User is of AccountType.ADMIN") {
            input?.accessor?.user?.accountType == User.AccountType.ADMIN
        }
        exitWithFailureIfTrue("Workspace has been archived or deleted") {
            input?.accessor?.workspace?.isArchived() == true
                    || input?.accessor?.workspace?.isDeleted() == true
        }
        exitWithFailureIfTrue("Page has been deleted") {
            input?.entity?.isDeleted() == true
        }
        // ToDo: think about parent page inheritance
        exitWithSuccessIfTrue("User / Member created this workspace") {
            (input?.accessor?.user != null
                    && input.accessor.user.uuid == input.entity?.creatorUUID)
                    || (input?.accessor?.member != null
                    && input.accessor.member.userUUID == input.entity?.creatorUUID)
        }
        exitWithSuccessIfTrue("Member has permission to modify this page") {
            val effectivePermission = input?.accessor?.member?.effectivePermission()
            effectivePermission?.hasWorkspacePermissionOrHigher(WorkspaceMember.Permissions.WorkspacePermission.CAN_ADMINISTRATE) == true
                    || effectivePermission?.hasPagePermissionOrHigher(WorkspaceMember.Permissions.PagePermission.CAN_VIEW_CREATE_EDIT) == true
                    || (input?.accessor?.member?.userUUID == input?.entity?.creatorUUID
                    && effectivePermission?.hasPagePermissionOrHigher(WorkspaceMember.Permissions.PagePermission.CAN_VIEW_CREATE) == true)
        }
    }
}