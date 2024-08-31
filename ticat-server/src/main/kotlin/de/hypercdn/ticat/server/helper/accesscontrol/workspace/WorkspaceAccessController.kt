package de.hypercdn.ticat.server.helper.accesscontrol.workspace

import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import de.hypercdn.ticat.server.data.sql.entities.workspace.member.WorkspaceMember
import de.hypercdn.ticat.server.helper.accesscontrol.AccessRequest
import de.hypercdn.ticat.server.helper.accesscontrol.AccessorContainer
import de.hypercdn.ticat.server.helper.accesscontrol.EntityAccessController
import de.hypercdn.ticat.server.helper.accesscontrol.workspace.rules.*

enum class WorkspaceExecutableAction : AccessRequest {
    CREATE_WORKSPACE,           // create new workspace
    VIEW_WORKSPACE,             // view workspace details
    MODIFY_WORKSPACE,           // modify workspace details
    MODIFY_MEMBERS,             // invite and manage members, modify permissions
    MODIFY_SETTINGS,            // modify workspace settings
    DELETE_WORKSPACE,           // delete workspace
    REVERT_FROM_HISTORY,        // revert workspace to state defined in history
    ACCESS_REQUEST,             // can join or request access to join workspace
}

class WorkspaceScopedAccessor(val workspace: Workspace? = null, val user: User? = null, val member: WorkspaceMember? = null): AccessorContainer {

    companion object {
        fun of(workspace: Workspace?, user: User?) = WorkspaceScopedAccessor(workspace, user, null)
        fun of(workspace: Workspace?, member: WorkspaceMember?) = WorkspaceScopedAccessor(workspace, null, member)
        fun of(member: WorkspaceMember?) = WorkspaceScopedAccessor(member?.workspace, null, member)
        fun of(workspace: Workspace?, user: User?, member: WorkspaceMember?) = WorkspaceScopedAccessor(workspace, user, member)
    }

    override fun isEmpty(): Boolean {
        return user == null && member == null
    }

}

class WorkspaceAccessor(val user: User? = null, val member: WorkspaceMember? = null): AccessorContainer {

    companion object {
        fun of(user: User?) = WorkspaceAccessor(user, null)
        fun of(member: WorkspaceMember?) = WorkspaceAccessor(null, member)
        fun of(user: User?, member: WorkspaceMember?) = WorkspaceAccessor(user, member)
    }

    override fun isEmpty(): Boolean {
        return user == null && member == null
    }

}

class WorkspaceAccessController : EntityAccessController<Workspace, WorkspaceExecutableAction, WorkspaceAccessor>() {
    override val accessRules = listOf(
            CreateWorkspaceRule(),
            ViewWorkspaceRule(),
            ModifyWorkspaceRule(),
            ModifyWorkspaceMembersAndSettingsRule(),
            DeleteWorkspaceRule(),
            RevertWorkspaceFromHistoryRule(),
            WorkspaceAccessRequestRule()
        )
}
