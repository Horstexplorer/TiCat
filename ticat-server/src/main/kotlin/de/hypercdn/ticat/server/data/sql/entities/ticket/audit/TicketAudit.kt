package de.hypercdn.ticat.server.data.sql.entities.ticket.audit

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.data.sql.base.audit.Audit
import de.hypercdn.ticat.server.data.sql.base.audit.ParentedAudit
import de.hypercdn.ticat.server.data.sql.entities.board.Board
import de.hypercdn.ticat.server.data.sql.entities.ticket.Ticket
import de.hypercdn.ticat.server.data.sql.entities.ticket.history.TicketHistory
import de.hypercdn.ticat.server.helper.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.util.*

@Entity
@Table(name = "audit_ticket")
@DynamicInsert
@DynamicUpdate
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
class TicketAudit : ParentedAudit<Board, Ticket, TicketAudit, TicketAudit.AuditAction> {

    enum class AuditAction {
        CREATED_TICKET,
        MODIFIED_TICKET_CONTENT,
        MODIFIED_TICKET_SETTINGS,
        ARCHIVED_TICKET,
        UNARCHIVED_TICKET,
        DELETED_TICKET,
        REVERTED_FROM_HISTORY
    }

    @Column(
        name = "change_history_uuid",
        updatable = false
    )
    @ColumnDefault("NULL")
    var changeHistoryUUID: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "change_history_uuid",
        referencedColumnName = "uuid",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    var changeHistory: TicketHistory? = null

    constructor(): super()

    constructor(other: TicketAudit): super(other) {
        this.changeHistoryUUID = other.changeHistoryUUID
    }

}