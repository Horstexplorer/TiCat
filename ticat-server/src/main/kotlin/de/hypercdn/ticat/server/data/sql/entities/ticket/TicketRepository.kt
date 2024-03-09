package de.hypercdn.ticat.server.data.sql.entities.ticket

import de.hypercdn.ticat.server.data.sql.entities.board.Board
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TicketRepository : CrudRepository<Ticket, UUID> {

    fun getTicketsByBoard(board: Board)

}