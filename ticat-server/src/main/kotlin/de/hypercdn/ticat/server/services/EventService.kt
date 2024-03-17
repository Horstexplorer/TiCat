package de.hypercdn.ticat.server.services

import de.hypercdn.ticat.server.events.publisher.UserEventPublisher
import org.springframework.stereotype.Service

/**
 * Service access to dispatch events internally
 */
@Service
class EventService (
    val userEvents: UserEventPublisher
) {

}