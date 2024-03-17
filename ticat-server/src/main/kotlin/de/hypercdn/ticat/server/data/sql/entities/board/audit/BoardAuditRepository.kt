package de.hypercdn.ticat.server.data.sql.entities.board.audit

import de.hypercdn.ticat.server.data.sql.entities.board.Board
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BoardAuditRepository : CrudRepository<BoardAudit, UUID> {

    @Query("""
        FROM BoardAudit boardAudit
        WHERE boardAudit.parentEntity = :workspace
    """)
    fun getBoardAuditsByWorkspace(workspace: Workspace): List<BoardAudit>

    @Query("""
        FROM BoardAudit boardAudit
        WHERE boardAudit.affectedEntity = :board
    """)
    fun getBoardAuditsByBoard(board: Board): List<BoardAudit>

}