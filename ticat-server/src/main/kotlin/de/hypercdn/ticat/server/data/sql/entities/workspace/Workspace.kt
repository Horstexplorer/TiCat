package de.hypercdn.ticat.server.data.sql.entities.workspace

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.config.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import de.hypercdn.ticat.server.data.helper.CopyConstructable
import de.hypercdn.ticat.server.data.sql.entities.user.User
import jakarta.persistence.*
import jakarta.persistence.Table
import org.hibernate.annotations.*
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "workspaces")
@DynamicInsert
@DynamicUpdate
@Cacheable(true)
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
class Workspace : CopyConstructable<Workspace> {

    companion object

    @Id
    @Column(
        name = "workspace_uuid",
        nullable = false,
        updatable = false
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    lateinit var uuid: UUID

    @Column(
        name = "human_id",
        nullable = false,
        updatable = false
    )
    lateinit var humanId: String

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

    @Embedded
    var settings: Settings = Settings()

    @Embeddable
    class Settings : CopyConstructable<Settings> {

        companion object

        @Column(
            name = "setting_visibility",
            nullable = false
        )
        @ColumnDefault("MEMBERS_ONLY")
        @Enumerated(EnumType.STRING)
        var visibility: Visibility = Visibility.MEMBERS_ONLY

        enum class Visibility {
            ANYONE,
            LOGGED_IN_USER,
            MEMBERS_ONLY
        }

        @Column(
            name = "setting_access_mode",
            nullable = false
        )
        @ColumnDefault("INVITE_ONLY")
        @Enumerated(EnumType.STRING)
        var accessMode: AccessMode = AccessMode.INVITE_ONLY

        enum class AccessMode {
            PUBLIC,
            REQUEST_BASED,
            INVITE_ONLY
        }

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
            this.visibility = other.visibility
            this.accessMode = other.accessMode
            this.status = other.status
        }

    }

    @Column(
        name = "title"
    )
    @ColumnDefault("NULL")
    var title: String? = null

    @Column(
        name = "description"
    )
    @ColumnDefault("NULL")
    var description: String? = null

    constructor()

    constructor(other: Workspace) {
        if (other::uuid.isInitialized)
            this.uuid = other.uuid
        if (other::humanId.isInitialized)
            this.humanId = other.humanId
        if (other::createdAt.isInitialized)
            this.createdAt = other.createdAt
        if (other::modifiedAt.isInitialized)
            this.modifiedAt = other.modifiedAt
        if (other::creatorUUID.isInitialized)
            this.creatorUUID = other.creatorUUID
        this.editorUUID = other.editorUUID
        this.settings = Settings(other.settings)
        this.title = other.title
        this.description = other.description
    }

}