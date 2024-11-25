package de.hypercdn.ticat.server.data.sql.entities.message.history

import com.fasterxml.jackson.annotation.JsonFilter
import de.hypercdn.ticat.server.helper.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import de.hypercdn.ticat.server.data.sql.base.history.History
import de.hypercdn.ticat.server.data.sql.entities.message.Message
import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate

@Entity
@Table(name = "message_history")
@DynamicInsert
@DynamicUpdate
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
class MessageHistory : History<Message, MessageHistory> {

    companion object

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