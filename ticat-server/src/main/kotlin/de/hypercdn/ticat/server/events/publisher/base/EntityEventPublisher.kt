package de.hypercdn.ticat.server.events.publisher.base

import de.hypercdn.ticat.server.data.sql.base.entity.BaseEntity
import de.hypercdn.ticat.server.helper.ModificationContext
import org.springframework.context.ApplicationEventPublisher

interface EntityPayload<T> where T : BaseEntity<T>

open class EntityCreatePayload<T>(
    val newEntity: T
): EntityPayload<T> where T : BaseEntity<T>

open class EntityModificationPayload<T>(
    val modificationContext: ModificationContext<T>
): EntityPayload<T> where T : BaseEntity<T>

open class EntityDeletePayload<ID, T>(
    val deletedEntityId: ID,
    val deletedEntity: T? = null
): EntityPayload<T> where T : BaseEntity<T>

interface EntityEvent<T, out P>: EventWithPayload<P> where T : BaseEntity<T>, P : EntityPayload<T>
open class GenericEntityEvent<T, P>(payload: P): GenericEventWithPayload<P>(payload), EntityEvent<T, P> where T : BaseEntity<T>, P : EntityPayload<T>

interface EntityCreateEvent<T, out P>: EntityEvent<T, P> where T : BaseEntity<T>, P : EntityCreatePayload<T>
open class GenericEntityCreateEvent<T, P>(payload: P): GenericEntityEvent<T, P>(payload), EntityCreateEvent<T, P> where T : BaseEntity<T>, P : EntityCreatePayload<T>

interface EntityModifyEvent<T, out P>: EntityEvent<T, P> where T : BaseEntity<T>, P : EntityModificationPayload<T>
open class GenericEntityModifyEvent<T, P>(payload: P): GenericEntityEvent<T, P>(payload), EntityModifyEvent<T, P> where T : BaseEntity<T>, P : EntityModificationPayload<T>

interface EntityDeleteEvent<T, out P>: EntityEvent<T, P> where T : BaseEntity<T>, P : EntityDeletePayload<*, T>
open class GenericEntityDeleteEvent<T, P>(payload: P): GenericEntityEvent<T, P>(payload), EntityDeleteEvent<T, P> where T : BaseEntity<T>, P : EntityDeletePayload<*, T>

open class EntityEventPublisher<T>(
    applicationEventPublisher: ApplicationEventPublisher
) : EventPublisher<T>(applicationEventPublisher) where T: EntityEvent<*,*>