package de.hypercdn.ticat.server.events.publisher.base

import org.springframework.context.ApplicationEventPublisher
import java.time.OffsetDateTime
import java.util.*

interface Event {
    val uuid: UUID
    val creationTimestamp: OffsetDateTime
    var dispatchTimestamp: OffsetDateTime
}

interface EventWithPayload<out T>: Event {
    val payload: T?
}

open class GenericEvent: Event {
    final override val uuid: UUID = UUID.randomUUID()
    final override val creationTimestamp: OffsetDateTime = OffsetDateTime.now()
    override lateinit var dispatchTimestamp: OffsetDateTime
}

open class GenericEventWithPayload<T>(override val payload: T?) : GenericEvent(), EventWithPayload<T>

open class EventPublisher<T : Event>(
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    fun dispatch(event: T) =
        applicationEventPublisher.publishEvent(event.apply { dispatchTimestamp = OffsetDateTime.now() })
}