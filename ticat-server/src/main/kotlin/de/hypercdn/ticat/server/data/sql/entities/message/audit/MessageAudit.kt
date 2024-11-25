package de.hypercdn.ticat.server.data.sql.entities.message.audit

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.data.sql.base.audit.Audit
import de.hypercdn.ticat.server.data.sql.entities.message.Message
import de.hypercdn.ticat.server.data.sql.entities.message.history.MessageHistory
import de.hypercdn.ticat.server.helper.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.util.*

@Entity
@Table(name = "audit_messages")
@DynamicInsert
@DynamicUpdate
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
class MessageAudit : Audit<Message, MessageAudit, MessageAudit.AuditAction> {

    enum class AuditAction {
        CREATED_MESSAGE,
        MODIFIED_MESSAGE_CONTENT,
        ARCHIVED_MESSAGE,
        DELETED_MESSAGE,
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
    var changeHistory: MessageHistory? = null

    constructor(): super()

    constructor(other: MessageAudit): super(other) {
        this.changeHistoryUUID = other.changeHistoryUUID
    }

}