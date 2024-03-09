package de.hypercdn.ticat.server.data.sql.entities.workspace.member

import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface WorkspaceMemberRepository : CrudRepository<WorkspaceMember, UUID> {

    fun getWorkspaceMemberByUser(user: User)

    fun getWorkspaceMembersByWorkspace(workspace: Workspace)

}