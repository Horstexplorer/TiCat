package de.hypercdn.ticat.server.data.json.entities.messages.history

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import de.hypercdn.ticat.server.data.json.entities.messages.MessageResponseJson
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJson
import de.hypercdn.ticat.server.data.sql.entities.messages.Message
import de.hypercdn.ticat.server.data.sql.entities.messages.history.MessageHistory
import java.time.OffsetDateTime
import java.util.*

class MessageHistoryResponseJson(
    @JsonIgnore
    val messageHistory: MessageHistory? = null
) {

    @JsonProperty(value = "uuid", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var uuid: UUID? = null

    @JsonProperty(value = "message", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var message: MessageResponseJson? = null

    @JsonProperty(value = "version", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var version: Int? = null

    @JsonProperty(value = "version_timestamp", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var versionTimestamp: OffsetDateTime? = null

    @JsonProperty(value = "editor", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var editor: UserResponseJson? = null

    @JsonProperty(value = "old_content", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var oldContent: String? = null

    @JsonProperty(value = "old_setting_status", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var oldSettingStatus: Message.Settings.Status? = null

}