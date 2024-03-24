package de.hypercdn.ticat.server.events.publisher.base

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
    val uuid: UUID
    val creationTimestamp: OffsetDateTime
    var dispatchTimestamp: OffsetDateTime
    val payload: Any?
}

interface TypedEvent<T>: Event {
    override val payload: T?
}

open class GenericEvent(override val payload: Any? = null) : Event {
    final override val uuid: UUID = UUID.randomUUID()
    final override val creationTimestamp: OffsetDateTime = OffsetDateTime.now()
    override lateinit var dispatchTimestamp: OffsetDateTime
}

open class GenericTypedEvent<T>(override val payload: T?) : GenericEvent(), TypedEvent<T>