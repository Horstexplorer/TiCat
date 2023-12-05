package de.hypercdn.ticat.server.data.sql.entities.ticket.history

import de.hypercdn.ticat.server.data.sql.entities.ticket.Ticket
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TicketHistoryRepository : CrudRepository<TicketHistory, UUID> {

    fun getTicketHistoriesByTicket(ticket: Ticket): List<TicketHistory>

}