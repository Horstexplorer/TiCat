package de.hypercdn.ticat.server.data.sql.entities.message.history

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.helper.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import de.hypercdn.ticat.server.data.helper.CopyConstructable
import de.hypercdn.ticat.server.data.sql.entities.history.History
import de.hypercdn.ticat.server.data.sql.entities.message.Message
import de.hypercdn.ticat.server.data.sql.entities.page.history.PageHistory
import de.hypercdn.ticat.server.data.sql.entities.user.User
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "message_history")
@DynamicInsert
@DynamicUpdate
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
class MessageHistory : History<MessageHistory>  {

    companion object

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "entity_reference_uuid",
        referencedColumnName = "message_uuid",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    lateinit var message: Message


    @Column(
        name = "old_content",
        updatable = false
    )
    var oldContent: String? = null

    @Column(
        name = "old_setting_status",
        updatable = false
    )
    var oldSettingStatus: Message.Settings.Status? = null

    constructor()

    constructor(other: MessageHistory): super(other)  {
        this.oldContent = other.oldContent
        this.oldSettingStatus = other.oldSettingStatus
    }

}