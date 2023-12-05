package de.hypercdn.ticat.server.events.publisher

import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.workspace.member.WorkspaceMember
import org.springframework.context.ApplicationEventPublisher

interface EntityEvent<T> : Event {
    val entity: T?
}
interface EntityCreateEvent<T> : EntityEvent<T>
interface EntityChangeEvent<T> : EntityEvent<T> {
    var newState: T
    var oldState: T?
}
interface EntityDeleteEvent<ID, T> : EntityEvent<T> {
    var entityID: ID
}
open class EntityCreateEventImp<T>(override val entity: T): EntityCreateEvent<T>, GenericEvent()
open class EntityChangeEventImp<T>(override val entity: T, override var newState: T, override var oldState: T?) : EntityChangeEvent<T>, GenericEvent()
open class EntityDeleteEventImp<ID, T>(override val entity: T?, override var entityID: ID) : EntityDeleteEvent<ID, T>, GenericEvent()

interface EventActorAttached {
    var actor: EventActorContainer
}

class EventActorContainer internal constructor(
    val user: User?,
    val member: WorkspaceMember?
) {
    companion object {
        fun of(user: User, member: WorkspaceMember? = null): EventActorContainer {
            member?.let { return EventActorContainer(member.user, member) }
            return EventActorContainer(user, null)
        }
    }
}

open class EntityEventPublisher<T : EntityEvent<*>>(applicationEventPublisher: ApplicationEventPublisher): EventPublisher<T>(applicationEventPublisher)