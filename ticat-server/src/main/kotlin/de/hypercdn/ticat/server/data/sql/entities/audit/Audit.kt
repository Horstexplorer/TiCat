package de.hypercdn.ticat.server.data.sql.entities.audit

import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.data.sql.entities.user.User
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.CreationTimestamp
import java.time.OffsetDateTime
import java.util.*

@MappedSuperclass
open class Audit<T, A> where T : Audit<T, A>, A : Enum<A> {

    companion object

    @Id
    @Column(
        name = "audit_uuid",
        nullable = false,
        updatable = false
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    lateinit var uuid: UUID

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
        referencedColumnName = "user_uuid",
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

}