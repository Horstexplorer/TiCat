package de.hypercdn.ticat.server.data.sql.base.audit

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.data.sql.base.entity.BaseEntity
import de.hypercdn.ticat.server.helper.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import java.util.*

@MappedSuperclass
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
open class ParentedAudit<P, E, T, A> : Audit<E, T, A> where P: BaseEntity<P>, E : BaseEntity<E>, T : ParentedAudit<P, E, T, A>, A : Enum<A> {

    @Column(
        name = "parent_entity_uuid",
        updatable = false
    )
    @ColumnDefault("NULL")
    var parentEntityUUID: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "parent_entity_uuid",
        referencedColumnName = "uuid",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    var parentEntity: P? = null

    @Column(
        name = "parent_entity_hint",
        updatable = false
    )
    @ColumnDefault("NULL")
    var parentEntityHint: String? = null

    constructor()

    constructor(other: T): super(other) {
        this.parentEntityUUID = other.parentEntityUUID
        this.parentEntityHint = other.parentEntityHint
    }

}