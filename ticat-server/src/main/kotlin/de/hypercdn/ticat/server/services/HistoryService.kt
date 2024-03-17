package de.hypercdn.ticat.server.services

import de.hypercdn.ticat.server.data.sql.entities.message.history.MessageHistoryRepository
import de.hypercdn.ticat.server.data.sql.entities.page.history.PageHistoryRepository
import de.hypercdn.ticat.server.data.sql.entities.ticket.history.TicketHistoryRepository
import de.hypercdn.ticat.server.data.sql.entities.workspace.history.WorkspaceHistoryRepository
import org.springframework.stereotype.Service

/**
 * Service to persist historical entity states
 */
@Service
class HistoryService (
    val messageHistoryRepository: MessageHistoryRepository,
    val pageHistoryRepository: PageHistoryRepository,
    val ticketHistoryRepository: TicketHistoryRepository,
    val workspaceHistoryRepository: WorkspaceHistoryRepository
) {

}