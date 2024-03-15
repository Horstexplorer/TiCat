package de.hypercdn.ticat.server.data.sql.base.history

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.data.sql.base.entity.BaseEntity
import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import de.hypercdn.ticat.server.helper.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.CreationTimestamp
import java.time.OffsetDateTime
import java.util.*

@MappedSuperclass
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
open class History<E, T> : BaseEntity<T> where E : BaseEntity<E>, T : History<E, T> {

    companion object

    @Column(
        name = "entity_reference_uuid",
        nullable = false,
        updatable = false
    )
    lateinit var entityReferenceUUID: UUID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "entity_reference_uuid",
        referencedColumnName = "uuid",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    lateinit var entity: E

    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    @ColumnDefault("NOW()")
    @CreationTimestamp
    lateinit var createdAt: OffsetDateTime

    @Column(
        name = "version_id",
        nullable = false,
        updatable = false
    )
    var versionId: Int = -1


    @Column(
        name = "editor_uuid",
        nullable = false,
        updatable = false
    )
    lateinit var editorUUID: UUID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "editor_uuid",
        referencedColumnName = "uuid",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    lateinit var editor: User

    constructor(): super()

    constructor(other: T): super(other) {
        if (other::entityReferenceUUID.isInitialized)
            this.entityReferenceUUID = other.entityReferenceUUID
        if (other::createdAt.isInitialized)
            this.createdAt = other.createdAt
        this.versionId = other.versionId
        if (other::editorUUID.isInitialized)
            this.editorUUID = other.editorUUID
    }

}