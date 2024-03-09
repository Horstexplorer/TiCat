package de.hypercdn.ticat.server.data.sql.base.audit

import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.data.sql.base.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import java.util.*

@MappedSuperclass
open class ParentedAudit<E, T, A> : Audit<E, T, A> where E : BaseEntity<E>, T : ParentedAudit<E, T, A>, A : Enum<A> {

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
    var parentEntity: E? = null

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