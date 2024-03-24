package de.hypercdn.ticat.server.events.publisher.entities

import de.hypercdn.ticat.server.data.sql.entities.ticket.Ticket
import de.hypercdn.ticat.server.events.publisher.base.*
import java.util.UUID

typealias TicketPayload = EntityPayload<Ticket>
typealias TicketCreatePayload = EntityCreatePayload<Ticket>
typealias TicketModificationPayload = EntityModificationPayload<Ticket>
typealias TicketDeletePayload = EntityDeletePayload<UUID, Ticket>

interface TicketEvent<T>: EntityEvent<T> where T : TicketPayload
open class GenericTicketEvent<T>(payload: T): GenericEntityEvent<T>(payload), TicketEvent<T> where T : TicketPayload

interface TicketCreateEvent: EntityCreateEvent<Ticket>, TicketEvent<TicketCreatePayload>
class TicketCreateEventImp(payload: TicketCreatePayload): GenericTicketEvent<TicketCreatePayload>(payload), TicketCreateEvent

interface TicketModificationEvent: EntityModificationEvent<Ticket>, TicketEvent<TicketModificationPayload>
class TicketModificationEventImp(payload: TicketModificationPayload): GenericTicketEvent<TicketModificationPayload>(payload), TicketModificationEvent

interface TicketDeleteEvent: EntityDeleteEvent<UUID, Ticket>, TicketEvent<TicketDeletePayload>
class TicketDeleteEventImp(payload: TicketDeletePayload): GenericTicketEvent<TicketDeletePayload>(payload), TicketDeleteEvent
