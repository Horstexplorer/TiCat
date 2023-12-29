package de.hypercdn.ticat.server.data.json.entities.workspace.member

import de.hypercdn.ticat.server.data.helper.EntityTemplateBuilder
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJson
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJsonBuilder
import de.hypercdn.ticat.server.data.json.entities.user.builder
import de.hypercdn.ticat.server.data.json.entities.workspace.WorkspaceResponseJson
import de.hypercdn.ticat.server.data.json.entities.workspace.WorkspaceResponseJsonBuilder
import de.hypercdn.ticat.server.data.json.entities.workspace.builder
import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import de.hypercdn.ticat.server.data.sql.entities.workspace.member.WorkspaceMember
import de.hypercdn.ticat.server.data.sql.entities.workspace.member.effectivePermission

class WorkspaceMemberResponseJsonBuilder(
    private val workspaceMember: WorkspaceMember? = null
): EntityTemplateBuilder<WorkspaceMemberResponseJsonBuilder, WorkspaceMemberResponseJson>({ WorkspaceMemberResponseJson() }) {

    fun includeUser(skip: Boolean = false, user: User? = workspaceMember?.user, configurator: (UserResponseJsonBuilder) -> Unit): WorkspaceMemberResponseJsonBuilder = modify(skip) {
        it.user = UserResponseJson.builder(user)
            .apply(configurator)
            .build()
    }

    fun includeWorkspace(skip: Boolean = false, workspace: Workspace? = workspaceMember?.workspace, configurator: (WorkspaceResponseJsonBuilder) -> Unit): WorkspaceMemberResponseJsonBuilder = modify(skip) {
        it.workspace = WorkspaceResponseJson.builder(workspace)
            .apply(configurator)
            .build()
    }

    fun includeVersionTimestamp(skip: Boolean = false): WorkspaceMemberResponseJsonBuilder = modify(skip) {
        it.versionTimestamp = workspaceMember?.modifiedAt
    }

    fun includeMembershipStatus(skip: Boolean = false): WorkspaceMemberResponseJsonBuilder = modify(skip) {
        it.membershipStatus = workspaceMember?.membershipStatus
    }

    enum class PermissionRule {
        SET_PERMISSIONS,
        EFFECTIVE_PERMISSIONS,
        ALL_PERMISSIONS
    }

    fun includePermission(rule: PermissionRule, skip: Boolean = false): WorkspaceMemberResponseJsonBuilder = modify(skip) {
        val setPermissions = WorkspaceMemberResponseJson.Permissions().apply {
            pagePermission = workspaceMember?.permissions?.pagePermission
            boardPermission = workspaceMember?.permissions?.boardPermission
            ticketPermission = workspaceMember?.permissions?.ticketPermission
            workspacePermission = workspaceMember?.permissions?.workspacePermission
        }
        val effectivePermission = workspaceMember?.effectivePermission()
        val effectivePermissions = WorkspaceMemberResponseJson.Permissions().apply {
            pagePermission = effectivePermission?.pagePermission
            boardPermission = effectivePermission?.boardPermission
            ticketPermission = effectivePermission?.ticketPermission
            workspacePermission = effectivePermission?.workspacePermission
        }
        when (rule) {
            PermissionRule.SET_PERMISSIONS -> {
                it.permissions = setPermissions
            }
            PermissionRule.EFFECTIVE_PERMISSIONS -> {
                it.effectivePermission = effectivePermissions
            }
            PermissionRule.ALL_PERMISSIONS -> {
                it.permissions = setPermissions
                it.effectivePermission = effectivePermissions
            }
        }
    }

}

fun WorkspaceMemberResponseJson.Companion.builder(workspaceMember: WorkspaceMember? = null): WorkspaceMemberResponseJsonBuilder = WorkspaceMemberResponseJsonBuilder(workspaceMember)
fun WorkspaceMember.toJsonResponseBuilder(): WorkspaceMemberResponseJsonBuilder = WorkspaceMemberResponseJsonBuilder(this)