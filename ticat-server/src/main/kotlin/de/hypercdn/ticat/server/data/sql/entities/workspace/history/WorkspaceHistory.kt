package de.hypercdn.ticat.server.data.sql.entities.workspace.history

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.config.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import de.hypercdn.ticat.server.data.helper.CopyConstructable
import de.hypercdn.ticat.server.data.sql.entities.ticket.Ticket
import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "workspace_history")
@DynamicInsert
@DynamicUpdate
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
class WorkspaceHistory : CopyConstructable<WorkspaceHistory> {

    companion object

    @Id
    @Column(
        name = "workspace_history_uuid",
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
        name = "version_id",
        nullable = false,
        updatable = false
    )
    var versionId: Int = -1

    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    @ColumnDefault("NOW()")
    @CreationTimestamp
    lateinit var createdAt: OffsetDateTime

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

    @Column(
        name = "old_title",
        updatable = false
    )
    @ColumnDefault("NULL")
    var oldTitle: String? = null

    @Column(
        name = "old_description",
        updatable = false
    )
    @ColumnDefault("NULL")
    var oldDescription: String? = null

    @Column(
        name = "old_setting_visibility",
        updatable = false
    )
    @ColumnDefault("NULL")
    @Enumerated(EnumType.STRING)
    var oldVisibility: Workspace.Settings.Visibility? = null

    @Column(
        name = "old_setting_access_mode",
        updatable = false
    )
    @ColumnDefault("NULL")
    @Enumerated(EnumType.STRING)
    var oldAccessMode: Workspace.Settings.AccessMode? = null

    @Column(
        name = "old_setting_status",
        updatable = false
    )
    @ColumnDefault("NULL")
    @Enumerated(EnumType.STRING)
    var oldStatus: Workspace.Settings.Status? = null

    constructor()

    constructor(other: WorkspaceHistory) {
        if (other::uuid.isInitialized)
            this.uuid = other.uuid
        if (other::workspaceUUID.isInitialized)
            this.workspaceUUID = other.workspaceUUID
        if (other::createdAt.isInitialized)
            this.createdAt = other.createdAt
        this.versionId = other.versionId
        if (other::editorUUID.isInitialized)
            this.editorUUID = other.editorUUID
        this.oldTitle = other.oldTitle
        this.oldDescription = other.oldDescription
        this.oldVisibility = other.oldVisibility
        this.oldAccessMode = other.oldAccessMode
        this.oldStatus = other.oldStatus
    }

}