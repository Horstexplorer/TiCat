package de.hypercdn.ticat.server.events.publisher.entities

import de.hypercdn.ticat.server.data.sql.entities.message.Message
import de.hypercdn.ticat.server.events.publisher.base.*
import de.hypercdn.ticat.server.helper.ModificationContext
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.util.*

interface MessagePayload: EntityPayload<Message>
open class MessageCreatePayload(
    newEntity: Message
) : EntityCreatePayload<Message>(newEntity), MessagePayload
open class MessageModificationPayload(
    modificationContext: ModificationContext<Message>
): EntityModificationPayload<Message>(modificationContext), MessagePayload
open class MessageDeletePayload(
    deletedEntityId: UUID,
    deletedEntity: Message? = null
): EntityDeletePayload<UUID, Message>(deletedEntityId, deletedEntity), MessagePayload

interface MessageEvent<out T>: EntityEvent<Message, T> where T : MessagePayload

interface MessageCreateEvent: EntityCreateEvent<Message, MessageCreatePayload>, MessageEvent<MessageCreatePayload>
open class MessageCreateEventImp(payload: MessageCreatePayload): GenericEntityCreateEvent<Message, MessageCreatePayload>(payload), MessageCreateEvent

interface MessageModificationEvent: EntityModifyEvent<Message, MessageModificationPayload>, MessageEvent<MessageModificationPayload>
open class MessageModificationEventImp(payload: MessageModificationPayload): GenericEntityModifyEvent<Message, MessageModificationPayload>(payload), MessageModificationEvent

interface MessageDeleteEvent: EntityDeleteEvent<Message, MessageDeletePayload>, MessageEvent<MessageDeletePayload>
open class MessageDeleteEventImp(payload: MessageDeletePayload): GenericEntityDeleteEvent<Message, MessageDeletePayload>(payload), MessageDeleteEvent

@Service
class MessageEventPublisher(
    applicationEventPublisher: ApplicationEventPublisher
) : EntityEventPublisher<MessageEvent<*>>(applicationEventPublisher) {
    fun publishMessageCreate(message: Message) = dispatch(MessageCreateEventImp(MessageCreatePayload(message)))

    fun publishMessageModification(context: ModificationContext<Message>) = dispatch(MessageModificationEventImp(MessageModificationPayload(context)))

    fun publishMessageDelete(id: UUID) = dispatch(MessageDeleteEventImp(MessageDeletePayload(id)))

    fun publishMessageDelete(message: Message) = dispatch(MessageDeleteEventImp(MessageDeletePayload(message.uuid, message)))
}