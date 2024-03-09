package de.hypercdn.ticat.server.data.sql.entities.ticket.history

import de.hypercdn.ticat.server.data.sql.entities.ticket.Ticket
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TicketHistoryRepository : CrudRepository<TicketHistory, UUID> {

    @Query("""
        FROM TicketHistory ticketHistory
        WHERE ticketHistory.entity = :ticket
    """)
    fun getTicketHistoriesByTicket(
        @Param("ticket") ticket: Ticket
    ): List<TicketHistory>

}