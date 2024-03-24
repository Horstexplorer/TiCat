package de.hypercdn.ticat.server.events.publisher.entities

import de.hypercdn.ticat.server.data.sql.entities.page.Page
import de.hypercdn.ticat.server.events.publisher.base.*
import java.util.UUID

typealias PagePayload = EntityPayload<Page>
typealias PageCreatePayload = EntityCreatePayload<Page>
typealias PageModificationPayload = EntityModificationPayload<Page>
typealias PageDeletePayload = EntityDeletePayload<UUID, Page>

interface PageEvent<T>: EntityEvent<T> where T : PagePayload
open class GenericPageEvent<T>(payload: T): GenericEntityEvent<T>(payload), PageEvent<T> where T : PagePayload

interface PageCreateEvent: EntityCreateEvent<Page>, PageEvent<PageCreatePayload>
class PageCreateEventImp(payload: PageCreatePayload): GenericPageEvent<PageCreatePayload>(payload), PageCreateEvent

interface PageModificationEvent: EntityModificationEvent<Page>, PageEvent<PageModificationPayload>
class PageModificationEventImp(payload: PageModificationPayload): GenericPageEvent<PageModificationPayload>(payload), PageModificationEvent

interface PageDeleteEvent: EntityDeleteEvent<UUID, Page>, PageEvent<PageDeletePayload>
class PageDeleteEventImp(payload: PageDeletePayload): GenericPageEvent<PageDeletePayload>(payload), PageDeleteEvent
