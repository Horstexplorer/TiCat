package de.hypercdn.ticat.server.data.json.entities.messages

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import de.hypercdn.ticat.server.data.json.entities.page.PageResponseJson
import de.hypercdn.ticat.server.data.json.entities.ticket.TicketResponseJson
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJson
import de.hypercdn.ticat.server.data.json.entities.workspace.WorkspaceResponseJson
import de.hypercdn.ticat.server.data.sql.entities.messages.Message
import java.time.OffsetDateTime
import java.util.*

class MessageResponseJson(
    @JsonIgnore
    val message: Message? = null
) {

    @JsonProperty(value = "uuid", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var uuid: UUID? = null

    @JsonProperty(value = "version_timestamp", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var versionTimestamp: OffsetDateTime? = null

    @JsonProperty(value = "sender", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var sender: UserResponseJson? = null

    @JsonProperty(value = "recipient", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var recipient: Recipient? = null

    class Recipient {

        @JsonProperty(value = "recipient_workspace", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var workspace: WorkspaceResponseJson? = null

        @JsonProperty(value = "recipient_page", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var page: PageResponseJson? = null

        @JsonProperty(value = "recipient_ticket", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var ticket: TicketResponseJson? = null

        @JsonProperty(value = "recipient_user", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var user: UserResponseJson? = null

    }

    @JsonProperty(value = "parent_message", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var parentMessage: MessageResponseJson? = null

    @JsonProperty(value = "child_messages", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var childMessages: List<MessageResponseJson>? = null

    @JsonProperty(value = "settings", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var settings: Settings? = null

    class Settings {

        @JsonProperty(value = "status", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var status: Message.Settings.Status? = null

    }

    @JsonProperty(value = "content", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var content: String? = null

}