package de.hypercdn.ticat.server.data.sql.entities.board

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.data.sql.base.audit.AuditAttachment
import de.hypercdn.ticat.server.data.sql.base.entity.BaseEntity
import de.hypercdn.ticat.server.data.sql.entities.board.audit.BoardAudit
import de.hypercdn.ticat.server.helper.constructor.CopyConstructable
import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import de.hypercdn.ticat.server.helper.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import jakarta.persistence.*
import jakarta.persistence.Table
import org.hibernate.annotations.*
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "boards")
@DynamicInsert
@DynamicUpdate
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
class Board : BaseEntity<Board>, AuditAttachment<BoardAudit> {

    companion object

    @Column(
        name = "workspace_uuid",
        nullable = false,
        updatable = false
    )
    lateinit var workspaceUUID: UUID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "workspace_uuid",
        referencedColumnName = "uuid",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    lateinit var workspace: Workspace

    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    @ColumnDefault("NOW()")
    @CreationTimestamp
    lateinit var createdAt: OffsetDateTime

    @Column(
        name = "modified_at",
        nullable = false
    )
    @ColumnDefault("NOW()")
    @UpdateTimestamp
    lateinit var modifiedAt: OffsetDateTime

    @Column(
        name = "creator_uuid",
        nullable = false,
        updatable = false
    )
    lateinit var creatorUUID: UUID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "creator_uuid",
        referencedColumnName = "uuid",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    lateinit var creator: User

    @Column(
        name = "editor_uuid"
    )
    var editorUUID: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "editor_uuid",
        referencedColumnName = "uuid",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    var editor: User? = null

    @Column(
        name = "title",
        nullable = false
    )
    @ColumnDefault("")
    var title: String = ""

    @Embedded
    var settings: Settings = Settings()

    @Embeddable
    class Settings : CopyConstructable<Settings> {

        companion object

        @Column(
            name = "setting_status",
            nullable = false
        )
        @ColumnDefault("ACTIVE")
        @Enumerated(EnumType.STRING)
        var status: Status = Status.ACTIVE

        enum class Status {
            ACTIVE,
            ARCHIVED,
            DELETED
        }

        constructor()

        constructor(other: Settings) {
            this.status = other.status
        }

    }

    constructor(): super()

    constructor(other: Board): super(other) {
        if (other::workspaceUUID.isInitialized)
            this.workspaceUUID = other.workspaceUUID
        if (other::createdAt.isInitialized)
            this.createdAt = other.createdAt
        if (other::modifiedAt.isInitialized)
            this.modifiedAt = other.modifiedAt
        if (other::creatorUUID.isInitialized)
            this.creatorUUID = other.creatorUUID
        this.editorUUID = other.editorUUID
        this.title = other.title
        this.settings = Settings(other.settings)
    }

}