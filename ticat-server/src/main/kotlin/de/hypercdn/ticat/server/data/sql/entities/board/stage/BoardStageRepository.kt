package de.hypercdn.ticat.server.data.sql.entities.board.stage

import de.hypercdn.ticat.server.data.sql.entities.board.Board
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface BoardStageRepository : CrudRepository<BoardStage, UUID> {

    fun getBoardStagesByBoard(board: Board): List<BoardStage>

}