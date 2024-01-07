package de.hypercdn.ticat.server.data.sql.entities.page

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.config.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import de.hypercdn.ticat.server.data.helper.CopyConstructable
import de.hypercdn.ticat.server.data.sql.entities.board.stage.BoardStage
import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import jakarta.persistence.*
import jakarta.persistence.Table
import org.hibernate.annotations.*
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "pages")
@DynamicInsert
@DynamicUpdate
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
class Page : CopyConstructable<Page> {

    companion object

    @Id
    @Column(
        name = "page_uuid",
        nullable = false,
        updatable = false
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    lateinit var uuid: UUID

    @Column(
        name = "workspace_uuid",
        nullable = false,
        updatable = false
    )
    lateinit var workspaceUUID: UUID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "workspace_uuid",
        referencedColumnName = "workspace_uuid",
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
        referencedColumnName = "user_uuid",
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
        referencedColumnName = "user_uuid",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    var editor: User? = null

    @Column(
        name = "title"
    )
    @ColumnDefault("NULL")
    var title: String? = null

    @Column(
        name = "content"
    )
    @ColumnDefault("NULL")
    var content: String? = null

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

        @Column(
            name = "setting_parent_page_uuid"
        )
        var parentPageUUID: UUID? = null

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(
            name = "setting_parent_page_uuid",
            referencedColumnName = "page_uuid",
            insertable = false,
            updatable = false
        )
        @JsonIgnore
        var parentPage: Page? = null

        constructor()

        constructor(other: Settings) {
            this.status = other.status
            this.parentPageUUID = parentPageUUID
        }

    }

    constructor()

    constructor(other: Page) {
        if (other::uuid.isInitialized)
            this.uuid = other.uuid
        if (other::workspaceUUID.isInitialized)
            this.workspaceUUID = other.workspaceUUID
        if (other::createdAt.isInitialized)
            this.createdAt = other.createdAt
        if (other::modifiedAt.isInitialized)
            this.modifiedAt = other.modifiedAt
        this.creatorUUID = other.creatorUUID
        this.editorUUID = other.editorUUID
        this.title = other.title
        this.content = other.content
        this.settings = Settings(other.settings)
    }

}