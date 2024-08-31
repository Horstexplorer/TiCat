package de.hypercdn.ticat.server.helper.accesscontrol

import de.hypercdn.ticat.server.data.sql.base.entity.BaseEntity
import jakarta.annotation.PostConstruct

abstract class EntityAccessController<T, A, E> : AccessController<T, A, E>() where T : BaseEntity<T>, A : AccessRequest, E : AccessorContainer {

    @PostConstruct
    private fun postInit() {
        logger.info("Initializing entity access controller with ${accessRules.size} rules")
    }

}