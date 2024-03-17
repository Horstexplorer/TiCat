package de.hypercdn.ticat.server.data.sql.entities.ticket.audit

import de.hypercdn.ticat.server.data.sql.entities.board.Board
import de.hypercdn.ticat.server.data.sql.entities.ticket.Ticket
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TicketAuditRepository : CrudRepository<TicketAudit, UUID> {

    @Query("""
        FROM TicketAudit ticketAudit
        WHERE ticketAudit.parentEntity = :board
    """)
    fun getTicketAuditsByBoard(board: Board): List<TicketAudit>

    @Query("""
        FROM TicketAudit ticketAudit
        WHERE ticketAudit.affectedEntity = :ticket
    """)
    fun getTicketAuditsByTicket(ticket: Ticket): List<TicketAudit>

}