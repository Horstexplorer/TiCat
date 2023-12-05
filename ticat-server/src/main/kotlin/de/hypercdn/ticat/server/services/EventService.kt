package de.hypercdn.ticat.server.services

import de.hypercdn.ticat.server.events.publisher.UserEventPublisher
import org.springframework.stereotype.Service

@Service
class EventService (
    val userEvents: UserEventPublisher
)