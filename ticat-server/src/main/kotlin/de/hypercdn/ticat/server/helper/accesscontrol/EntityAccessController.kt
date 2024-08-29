package de.hypercdn.ticat.server.helper.accesscontrol

import de.hypercdn.ticat.server.data.sql.base.entity.BaseEntity

abstract class EntityAccessController<T, A, E> : AccessController<T, A, E>() where T : BaseEntity<T>, A : AccessRequest, E : AccessorContainer