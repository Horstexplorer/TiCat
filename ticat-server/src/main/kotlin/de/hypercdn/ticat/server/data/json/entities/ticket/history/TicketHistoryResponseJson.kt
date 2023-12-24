package de.hypercdn.ticat.server.data.json.entities.ticket.history

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import de.hypercdn.ticat.server.data.json.entities.board.stage.BoardStageResponseJson
import de.hypercdn.ticat.server.data.json.entities.ticket.TicketResponseJson
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJson
import de.hypercdn.ticat.server.data.sql.entities.ticket.Ticket
import de.hypercdn.ticat.server.data.sql.entities.ticket.history.TicketHistory
import java.time.OffsetDateTime
import java.util.*

class TicketHistoryResponseJson {

    companion object

    @JsonProperty(value = "uuid", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var uuid: UUID? = null

    @JsonProperty(value = "ticket", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var ticket: TicketResponseJson? = null

    @JsonProperty(value = "version", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var version: Int? = null

    @JsonProperty(value = "version_timestamp", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var versionTimestamp: OffsetDateTime? = null

    @JsonProperty(value = "editor", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var editor: UserResponseJson? = null

    @JsonProperty(value = "old_title", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var oldTitle: String? = null

    @JsonProperty(value = "old_content", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var oldContent: String? = null

    @JsonProperty(value = "old_status", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var oldStatus: Ticket.Settings.Status? = null

    @JsonProperty(value = "old_board_stage", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var oldBoardStage: BoardStageResponseJson? = null

    @JsonProperty(value = "old_assignee", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var oldAssignee: UserResponseJson? = null

}