package de.hypercdn.ticat.server.data.sql.entities.ticket.history

import com.fasterxml.jackson.annotation.JsonFilter
import de.hypercdn.ticat.server.helper.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import de.hypercdn.ticat.server.helper.constructor.CopyConstructable
import de.hypercdn.ticat.server.data.sql.base.history.History
import de.hypercdn.ticat.server.data.sql.entities.ticket.Ticket
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.util.*

@Entity
@Table(name = "ticket_history")
@DynamicInsert
@DynamicUpdate
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
class TicketHistory : History<Ticket, TicketHistory>, CopyConstructable<TicketHistory> {

    companion object

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

    constructor(): super()

    constructor(other: TicketHistory): super(other) {
        this.oldTitle = other.oldTitle
        this.oldContent = other.oldContent
        this.oldStatus = other.oldStatus
        this.oldStageUUID = other.oldStageUUID
        this.oldAssigneeUUID = other.oldAssigneeUUID
    }

}