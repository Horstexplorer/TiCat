package de.hypercdn.ticat.server.events.publisher

import org.springframework.context.ApplicationEventPublisher
import java.time.OffsetDateTime
import java.util.UUID

open class EventPublisher<T : Event>(
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    fun dispatch(event: T) =
        applicationEventPublisher.publishEvent(event.apply { dispatchTimestamp = OffsetDateTime.now() })
}

interface Event {
    var uuid: UUID
    var dispatchTimestamp: OffsetDateTime
}

abstract class GenericEvent : Event {
    final override var uuid: UUID = UUID.randomUUID()
    override lateinit var dispatchTimestamp: OffsetDateTime
}