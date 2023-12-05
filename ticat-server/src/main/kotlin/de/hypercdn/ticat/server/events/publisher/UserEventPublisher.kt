package de.hypercdn.ticat.server.events.publisher

import de.hypercdn.ticat.server.data.sql.entities.user.User
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

interface UserEvent: EntityEvent<User>
interface UserChangeEvent: UserEvent, EntityChangeEvent<User>, EventActorAttached
interface UserJwtUpdateEvent: UserEvent, EntityChangeEvent<User>

class UserChangeEventImp(entity: User, newState: User = entity, oldState: User?, override var actor: EventActorContainer) : UserChangeEvent, EntityChangeEventImp<User>(entity, newState, oldState)
class UserJwtChangeEventImp(entity: User, newState: User = entity, oldState: User?) : UserJwtUpdateEvent, EntityChangeEventImp<User>(entity, newState, oldState)

@Component
class UserEventPublisher(
    publisher: ApplicationEventPublisher
) : EntityEventPublisher<UserEvent>(publisher) {
    fun dispatchUserChangeEvent(newState: User, oldState: User? = null, actor: EventActorContainer) = dispatch(UserChangeEventImp(newState, newState, oldState, actor))
    fun dispatchUserJwtChangeEvent(newState: User, oldState: User? = null) = dispatch(UserJwtChangeEventImp(newState, newState, oldState))
}
