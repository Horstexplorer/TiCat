package de.hypercdn.ticat.server.events.publisher.entities

import de.hypercdn.ticat.server.data.sql.entities.ticket.Ticket
import de.hypercdn.ticat.server.events.publisher.base.*
import de.hypercdn.ticat.server.helper.ModificationContext
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.util.*

interface TicketPayload: EntityPayload<Ticket>
open class TicketCreatePayload(
    newEntity: Ticket
) : EntityCreatePayload<Ticket>(newEntity), TicketPayload
open class TicketModificationPayload(
    modificationContext: ModificationContext<Ticket>
): EntityModificationPayload<Ticket>(modificationContext), TicketPayload
open class TicketDeletePayload(
    deletedEntityId: UUID,
    deletedEntity: Ticket? = null
): EntityDeletePayload<UUID, Ticket>(deletedEntityId, deletedEntity), TicketPayload

interface TicketEvent<out T>: EntityEvent<Ticket, T> where T : TicketPayload

interface TicketCreateEvent: EntityCreateEvent<Ticket, TicketCreatePayload>, TicketEvent<TicketCreatePayload>
open class TicketCreateEventImp(payload: TicketCreatePayload): GenericEntityCreateEvent<Ticket, TicketCreatePayload>(payload), TicketCreateEvent

interface TicketModificationEvent: EntityModifyEvent<Ticket, TicketModificationPayload>, TicketEvent<TicketModificationPayload>
open class TicketModificationEventImp(payload: TicketModificationPayload): GenericEntityModifyEvent<Ticket, TicketModificationPayload>(payload), TicketModificationEvent

interface TicketDeleteEvent: EntityDeleteEvent<Ticket, TicketDeletePayload>, TicketEvent<TicketDeletePayload>
open class TicketDeleteEventImp(payload: TicketDeletePayload): GenericEntityDeleteEvent<Ticket, TicketDeletePayload>(payload), TicketDeleteEvent

@Service
class TicketEventPublisher(
    applicationEventPublisher: ApplicationEventPublisher
) : EntityEventPublisher<TicketEvent<*>>(applicationEventPublisher) {
    fun publishTicketCreate(ticket: Ticket) = dispatch(TicketCreateEventImp(TicketCreatePayload(ticket)))

    fun publishTicketModification(context: ModificationContext<Ticket>) = dispatch(TicketModificationEventImp(TicketModificationPayload(context)))

    fun publishTicketDelete(id: UUID) = dispatch(TicketDeleteEventImp(TicketDeletePayload(id)))

    fun publishTicketDelete(ticket: Ticket) = dispatch(TicketDeleteEventImp(TicketDeletePayload(ticket.uuid, ticket)))
}