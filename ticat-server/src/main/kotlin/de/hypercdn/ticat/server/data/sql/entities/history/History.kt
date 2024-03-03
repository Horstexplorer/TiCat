package de.hypercdn.ticat.server.data.sql.entities.history

import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.data.helper.CopyConstructable
import de.hypercdn.ticat.server.data.sql.entities.user.User
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.CreationTimestamp
import java.time.OffsetDateTime
import java.util.*

@MappedSuperclass
open class History<T> : CopyConstructable<T> where T : History<T>, T : CopyConstructable<T> {

    companion object

    @Id
    @Column(
        name = "history_uuid",
        nullable = false,
        updatable = false
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    lateinit var uuid: UUID

    @Column(
        name = "entity_reference_uuid",
        nullable = false,
        updatable = false
    )
    lateinit var entityReferenceUUID: UUID

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
        referencedColumnName = "user_uuid",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    lateinit var editor: User

    constructor()

    constructor(other: T) {
        if (other::uuid.isInitialized)
            this.uuid = other.uuid
        if (other::entityReferenceUUID.isInitialized)
            this.entityReferenceUUID = other.entityReferenceUUID
        if (other::createdAt.isInitialized)
            this.createdAt = other.createdAt
        this.versionId = other.versionId
        if (other::editorUUID.isInitialized)
            this.editorUUID = other.editorUUID
    }

}