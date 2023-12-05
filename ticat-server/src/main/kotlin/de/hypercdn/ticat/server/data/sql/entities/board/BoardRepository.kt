package de.hypercdn.ticat.server.data.sql.entities.board

import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface BoardRepository : CrudRepository<Board, UUID> {

    fun getBoardsByWorkspace(workspace: Workspace): List<Board>

}