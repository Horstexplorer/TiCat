package de.hypercdn.ticat.server.events.publisher.entities

import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.events.publisher.base.*
import java.util.UUID

typealias UserPayload = EntityPayload<User>
typealias UserCreatePayload = EntityCreatePayload<User>
typealias UserModificationPayload = EntityModificationPayload<User>
typealias UserDeletePayload = EntityDeletePayload<UUID, User>

interface UserEvent<T>: EntityEvent<T> where T : UserPayload
open class GenericUserEvent<T>(payload: T): GenericEntityEvent<T>(payload), UserEvent<T> where T : UserPayload

interface UserCreateEvent: EntityCreateEvent<User>, UserEvent<UserCreatePayload>
class UserCreateEventImp(payload: UserCreatePayload): GenericUserEvent<UserCreatePayload>(payload), UserCreateEvent

interface UserModificationEvent: EntityModificationEvent<User>, UserEvent<UserModificationPayload>
class UserModificationEventImp(payload: UserModificationPayload): GenericUserEvent<UserModificationPayload>(payload), UserModificationEvent

interface UserDeleteEvent: EntityDeleteEvent<UUID, User>, UserEvent<UserDeletePayload>
class UserDeleteEventImp(payload: UserDeletePayload): GenericUserEvent<UserDeletePayload>(payload), UserDeleteEvent
