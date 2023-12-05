package de.hypercdn.ticat.server.data.sql.entities.ticket.history

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.config.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import de.hypercdn.ticat.server.data.helper.CopyConstructable
import de.hypercdn.ticat.server.data.sql.entities.page.history.PageHistory
import de.hypercdn.ticat.server.data.sql.entities.ticket.Ticket
import de.hypercdn.ticat.server.data.sql.entities.user.User
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "ticket_history")
@DynamicInsert
@DynamicUpdate
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
class TicketHistory : CopyConstructable<PageHistory> {

    companion object

    @Id
    @Column(
        name = "ticket_history_uuid",
        nullable = false,
        updatable = false
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    lateinit var uuid: UUID

    @Column(
        name = "ticket_uuid",
        nullable = false,
        updatable = false
    )
    lateinit var ticketUUID: UUID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "ticket_uuid",
        referencedColumnName = "ticket_uuid",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    lateinit var ticket: Ticket

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

    @Column(
        name = "old_title",
        updatable = false
    )
    @ColumnDefault("NULL")
    var oldTitle: String? = null

    @Column(
        name = "old_content",
        updatable = false
    )
    @ColumnDefault("NULL")
    var oldContent: String? = null

    @Column(
        name = "old_setting_status",
        updatable = false
    )
    @ColumnDefault("NULL")
    @Enumerated(EnumType.STRING)
    var oldStatus: Ticket.Settings.Status? = null

    @Column(
        name = "old_setting_board_stage_uuid",
        updatable = false
    )
    @ColumnDefault("NULL")
    var oldStageUUID: UUID? = null

    @Column(
        name = "old_setting_assignee_uuid",
        updatable = false
    )
    @ColumnDefault("NULL")
    var oldAssigneeUUID: UUID? = null

    constructor()

    constructor(other: TicketHistory) {
        if (other::uuid.isInitialized)
            this.uuid = other.uuid
        if (other::ticketUUID.isInitialized)
            this.ticketUUID = other.ticketUUID
        if (other::createdAt.isInitialized)
            this.createdAt = other.createdAt
        this.versionId = other.versionId
        if (other::editorUUID.isInitialized)
            this.editorUUID = other.editorUUID
        this.oldTitle = other.oldTitle
        this.oldContent = other.oldContent
        this.oldStatus = other.oldStatus
        this.oldStageUUID = other.oldStageUUID
        this.oldAssigneeUUID = other.oldAssigneeUUID
    }

}