package de.hypercdn.ticat.server.events.publisher.entities

import de.hypercdn.ticat.server.data.sql.entities.page.Page
import de.hypercdn.ticat.server.events.publisher.base.*
import de.hypercdn.ticat.server.helper.ModificationContext
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.util.*

interface PagePayload: EntityPayload<Page>
open class PageCreatePayload(
    newEntity: Page
) : EntityCreatePayload<Page>(newEntity), PagePayload
open class PageModificationPayload(
    modificationContext: ModificationContext<Page>
): EntityModificationPayload<Page>(modificationContext), PagePayload
open class PageDeletePayload(
    deletedEntityId: UUID,
    deletedEntity: Page? = null
): EntityDeletePayload<UUID, Page>(deletedEntityId, deletedEntity), PagePayload

interface PageEvent<out T>: EntityEvent<Page, T> where T : PagePayload

interface PageCreateEvent: EntityCreateEvent<Page, PageCreatePayload>, PageEvent<PageCreatePayload>
open class PageCreateEventImp(payload: PageCreatePayload): GenericEntityCreateEvent<Page, PageCreatePayload>(payload), PageCreateEvent

interface PageModificationEvent: EntityModifyEvent<Page, PageModificationPayload>, PageEvent<PageModificationPayload>
open class PageModificationEventImp(payload: PageModificationPayload): GenericEntityModifyEvent<Page, PageModificationPayload>(payload), PageModificationEvent

interface PageDeleteEvent: EntityDeleteEvent<Page, PageDeletePayload>, PageEvent<PageDeletePayload>
open class PageDeleteEventImp(payload: PageDeletePayload): GenericEntityDeleteEvent<Page, PageDeletePayload>(payload), PageDeleteEvent

@Service
class PageEventPublisher(
    applicationEventPublisher: ApplicationEventPublisher
) : EntityEventPublisher<PageEvent<*>>(applicationEventPublisher) {
    fun publishPageCreate(page: Page) = dispatch(PageCreateEventImp(PageCreatePayload(page)))

    fun publishPageModification(context: ModificationContext<Page>) = dispatch(PageModificationEventImp(PageModificationPayload(context)))

    fun publishPageDelete(id: UUID) = dispatch(PageDeleteEventImp(PageDeletePayload(id)))

    fun publishPageDelete(page: Page) = dispatch(PageDeleteEventImp(PageDeletePayload(page.uuid, page)))
}