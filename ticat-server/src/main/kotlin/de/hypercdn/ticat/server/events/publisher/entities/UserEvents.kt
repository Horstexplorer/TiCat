package de.hypercdn.ticat.server.events.publisher.entities

import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.events.publisher.base.*
import de.hypercdn.ticat.server.helper.ModificationContext
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.util.*

interface UserPayload: EntityPayload<User>
open class UserCreatePayload(
    newEntity: User
) : EntityCreatePayload<User>(newEntity), UserPayload
open class UserModificationPayload(
    modificationContext: ModificationContext<User>
): EntityModificationPayload<User>(modificationContext), UserPayload
open class UserDeletePayload(
    deletedEntityId: UUID,
    deletedEntity: User? = null
): EntityDeletePayload<UUID, User>(deletedEntityId, deletedEntity), UserPayload

interface UserEvent<out T>: EntityEvent<User, T> where T : UserPayload

interface UserCreateEvent: EntityCreateEvent<User, UserCreatePayload>, UserEvent<UserCreatePayload>
open class UserCreateEventImp(payload: UserCreatePayload): GenericEntityCreateEvent<User, UserCreatePayload>(payload), UserCreateEvent

interface UserModificationEvent: EntityModifyEvent<User, UserModificationPayload>, UserEvent<UserModificationPayload>
open class UserModificationEventImp(payload: UserModificationPayload): GenericEntityModifyEvent<User, UserModificationPayload>(payload), UserModificationEvent

interface UserDeleteEvent: EntityDeleteEvent<User, UserDeletePayload>, UserEvent<UserDeletePayload>
open class UserDeleteEventImp(payload: UserDeletePayload): GenericEntityDeleteEvent<User, UserDeletePayload>(payload), UserDeleteEvent

@Service
class UserEventPublisher(
    applicationEventPublisher: ApplicationEventPublisher
) : EntityEventPublisher<UserEvent<*>>(applicationEventPublisher) {
    fun publishUserCreate(user: User) = dispatch(UserCreateEventImp(UserCreatePayload(user)))

    fun publishUserModification(context: ModificationContext<User>) = dispatch(UserModificationEventImp(UserModificationPayload(context)))

    fun publishUserDelete(id: UUID) = dispatch(UserDeleteEventImp(UserDeletePayload(id)))

    fun publishUserDelete(user: User) = dispatch(UserDeleteEventImp(UserDeletePayload(user.uuid, user)))
}