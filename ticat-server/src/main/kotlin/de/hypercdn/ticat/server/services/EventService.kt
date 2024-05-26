package de.hypercdn.ticat.server.services

import de.hypercdn.ticat.server.events.publisher.entities.*
import org.springframework.stereotype.Service

/**
 * Service access to dispatch events internally
 */
@Service
class EventService (
    var boardPublisher: BoardEventPublisher,
    var messagePublisher: MessageEventPublisher,
    var pagePublisher: PageEventPublisher,
    var ticketPublisher: TicketEventPublisher,
    var userPublisher: UserEventPublisher,
    var workspacePublisher: WorkspaceEventPublisher,
)