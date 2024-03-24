package de.hypercdn.ticat.server.events.publisher

import de.hypercdn.ticat.server.data.sql.base.entity.BaseEntity
import de.hypercdn.ticat.server.helper.ModificationContext

interface EntityPayload<T> where T : BaseEntity<T>

interface EntityCreatePayload<T>: EntityPayload<T> where T : BaseEntity<T> {
    val newEntity: T
}

interface EntityModificationPayload<T>: EntityPayload<T> where T : BaseEntity<T> {
    val modificationContext: ModificationContext<T>
}

interface EntityDeletePayload<ID, T>: EntityPayload<T> where T : BaseEntity<T> {
    val deletedEntityId: ID
    val deletedEntity: T?
}

interface EntityEvent<T>: TypedEvent<T> where T : EntityPayload<*>
open class GenericEntityEvent<T>(payload: T) : GenericTypedEvent<T>(payload), EntityEvent<T> where T : EntityPayload<*>

interface EntityCreateEvent<T>: EntityEvent<EntityCreatePayload<T>> where T : BaseEntity<T>
open class GenericEntityCreateEvent<T>(payload: EntityCreatePayload<T>) : GenericEntityEvent<EntityCreatePayload<T>>(payload), EntityCreateEvent<T> where T : BaseEntity<T>

interface EntityModificationEvent<T> : EntityEvent<EntityModificationPayload<T>> where T : BaseEntity<T>
open class GenericEntityModificationEvent<T>(payload: EntityModificationPayload<T>) : GenericEntityEvent<EntityModificationPayload<T>>(payload), EntityModificationEvent<T> where T : BaseEntity<T>

interface EntityDeleteEvent<ID, T>: EntityEvent<EntityDeletePayload<ID, T>> where T : BaseEntity<T>
open class GenericEntityDeleteEvent<ID, T>(payload: EntityDeletePayload<ID, T>) : GenericEntityEvent<EntityDeletePayload<ID, T>>(payload), EntityDeleteEvent<ID, T> where T : BaseEntity<T>