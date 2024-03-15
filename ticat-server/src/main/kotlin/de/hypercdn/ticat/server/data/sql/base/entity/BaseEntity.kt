package de.hypercdn.ticat.server.data.sql.base.entity

import com.fasterxml.jackson.annotation.JsonFilter
import de.hypercdn.ticat.server.helper.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import de.hypercdn.ticat.server.helper.constructor.CopyConstructable
import jakarta.persistence.*
import java.util.*

@MappedSuperclass
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
open class BaseEntity<T> : CopyConstructable<T> where T : BaseEntity<T> {

    companion object

    @Id
    @Column(
        name = "uuid",
        nullable = false,
        updatable = false
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    lateinit var uuid: UUID

    constructor()

    constructor(other: T) {
        if (other::uuid.isInitialized)
            this.uuid = other.uuid
    }

}