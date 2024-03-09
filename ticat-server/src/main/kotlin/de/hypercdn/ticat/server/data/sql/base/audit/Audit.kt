package de.hypercdn.ticat.server.data.sql.base.audit

import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.data.sql.base.entity.BaseEntity
import de.hypercdn.ticat.server.data.sql.entities.user.User
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.CreationTimestamp
import java.time.OffsetDateTime
import java.util.*

@MappedSuperclass
open class Audit<E, T, A> : BaseEntity<T> where E : BaseEntity<E>, T : Audit<E, T, A>, A : Enum<A> {

    companion object

    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    @ColumnDefault("NOW()")
    @CreationTimestamp
    lateinit var createdAt: OffsetDateTime

    @Column(
        name = "action",
        nullable = false,
        updatable = false
    )
    @Enumerated(EnumType.STRING)
    lateinit var action: A

    @Column(
        name = "action_description",
        updatable = false
    )
    @ColumnDefault("NULL")
    var actionDescription: String? = null

    @Column(
        name = "actor_entity_uuid",
        updatable = false
    )
    @ColumnDefault("NULL")
    var actorEntityUUID: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "actor_entity_uuid",
        referencedColumnName = "uuid",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    var actor: User? = null

    @Column(
        name = "actor_entity_hint",
        updatable = false
    )
    @ColumnDefault("NULL")
    var actorHint: String? = null

    @Column(
        name = "affected_entity_uuid",
        updatable = false
    )
    @ColumnDefault("NULL")
    var affectedEntityUUID: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "affected_entity_uuid",
        referencedColumnName = "uuid",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    var affectedEntity: E? = null

    @Column(
        name = "affected_entity_hint",
        updatable = false
    )
    @ColumnDefault("NULL")
    var affectedEntityHint: String? = null

    constructor(): super()

    constructor(other: T): super(other) {
        if (other::createdAt.isInitialized)
            this.createdAt = other.createdAt
        if (other::action.isInitialized)
            this.action = other.action
        this.actionDescription = other.actionDescription
        this.actorEntityUUID = other.actorEntityUUID
        this.actorHint = other.actorHint
        this.affectedEntityUUID = other.affectedEntityUUID
        this.affectedEntityHint = other.affectedEntityHint
    }

}