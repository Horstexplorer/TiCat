package de.hypercdn.ticat.server.data.sql.entities.ticket

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.data.sql.base.entity.BaseEntity
import de.hypercdn.ticat.server.data.sql.base.history.HistoryAttachment
import de.hypercdn.ticat.server.helper.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import de.hypercdn.ticat.server.helper.constructor.CopyConstructable
import de.hypercdn.ticat.server.data.sql.entities.board.Board
import de.hypercdn.ticat.server.data.sql.entities.board.stage.BoardStage
import de.hypercdn.ticat.server.data.sql.entities.page.Page
import de.hypercdn.ticat.server.data.sql.entities.ticket.history.TicketHistory
import de.hypercdn.ticat.server.data.sql.entities.user.User
import jakarta.persistence.*
import jakarta.persistence.Table
import org.hibernate.annotations.*
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "tickets")
@DynamicInsert
@DynamicUpdate
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
class Ticket : BaseEntity<Ticket>, HistoryAttachment<TicketHistory> {

    companion object

    @Column(
        name = "board_uuid",
        nullable = false,
        updatable = false
    )
    lateinit var boardUUID: UUID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "board_uuid",
        referencedColumnName = "uuid",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    lateinit var board: Board

    @Column(
        name = "ticket_series_id",
        updatable = false
    )
    @ColumnDefault("-1")
    var seriesId: Int = -1

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
            name = "setting_board_stage_uuid"
        )
        @ColumnDefault("NULL")
        var stageUUID: UUID? = null

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(
            name = "setting_board_stage_uuid",
            referencedColumnName = "uuid",
            insertable = false,
            updatable = false
        )
        @JsonIgnore
        var stage: BoardStage? = null

        @Column(
            name = "setting_assignee_uuid"
        )
        var assigneeUUID: UUID? = null

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(
            name = "setting_assignee_uuid",
            referencedColumnName = "uuid",
            insertable = false,
            updatable = false
        )
        @JsonIgnore
        var assignee: User? = null

        constructor()

        constructor(other: Settings) {
            this.status = other.status
            this.stageUUID = other.stageUUID
            this.assigneeUUID = other.assigneeUUID
        }

    }

    @Column(
        name = "title",
        nullable = false
    )
    @ColumnDefault("")
    var title: String = ""

    @Column(
        name = "content",
        nullable = false
    )
    @ColumnDefault("")
    var content: String = ""

    constructor(): super()

    constructor(other: Ticket): super(other) {
        if (other::boardUUID.isInitialized)
            this.boardUUID = other.boardUUID
        this.seriesId = other.seriesId
        if (other::createdAt.isInitialized)
            this.createdAt = other.createdAt
        if (other::modifiedAt.isInitialized)
            this.modifiedAt = other.modifiedAt
        if (other::creatorUUID.isInitialized)
            this.creatorUUID = other.creatorUUID
        this.editorUUID = other.editorUUID
        this.settings = Settings(other.settings)
        this.title = other.title
        this.content = other.content
    }

}