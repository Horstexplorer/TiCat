package de.hypercdn.ticat.server.events.publisher.entities

import de.hypercdn.ticat.server.data.sql.entities.message.Message
import de.hypercdn.ticat.server.events.publisher.base.*
import java.util.UUID

typealias MessagePayload = EntityPayload<Message>
typealias MessageCreatePayload = EntityCreatePayload<Message>
typealias MessageModificationPayload = EntityModificationPayload<Message>
typealias MessageDeletePayload = EntityDeletePayload<UUID, Message>

interface MessageEvent<T>: EntityEvent<T> where T : MessagePayload
open class GenericMessageEvent<T>(payload: T): GenericEntityEvent<T>(payload), MessageEvent<T> where T : MessagePayload

interface MessageCreateEvent: EntityCreateEvent<Message>, MessageEvent<MessageCreatePayload>
class MessageCreateEventImp(payload: MessageCreatePayload): GenericMessageEvent<MessageCreatePayload>(payload), MessageCreateEvent

interface MessageModificationEvent: EntityModificationEvent<Message>, MessageEvent<MessageModificationPayload>
class MessageModificationEventImp(payload: MessageModificationPayload): GenericMessageEvent<MessageModificationPayload>(payload), MessageModificationEvent

interface MessageDeleteEvent: EntityDeleteEvent<UUID, Message>, MessageEvent<MessageDeletePayload>
class MessageDeleteEventImp(payload: MessageDeletePayload): GenericMessageEvent<MessageDeletePayload>(payload), MessageDeleteEvent
