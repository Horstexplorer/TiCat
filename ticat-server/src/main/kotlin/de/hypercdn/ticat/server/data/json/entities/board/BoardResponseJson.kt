package de.hypercdn.ticat.server.data.json.entities.board

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import de.hypercdn.ticat.server.data.json.entities.board.stage.BoardStageResponseJson
import de.hypercdn.ticat.server.data.json.entities.messages.MessageResponseJson
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJson
import de.hypercdn.ticat.server.data.json.entities.workspace.WorkspaceResponseJson
import de.hypercdn.ticat.server.data.sql.entities.board.Board
import de.hypercdn.ticat.server.data.sql.entities.board.stage.BoardStage
import java.time.OffsetDateTime
import java.util.*

class BoardResponseJson{

    companion object

    @JsonProperty(value = "uuid", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var uuid: UUID? = null

    @JsonProperty(value = "workspace", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var workspace: WorkspaceResponseJson? = null

    @JsonProperty(value = "version_timestamp", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var versionTimestamp: OffsetDateTime? = null

    @JsonProperty(value = "creator", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var creator: UserResponseJson? = null

    @JsonProperty(value = "editor", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var editor: UserResponseJson? = null

    @JsonProperty(value = "settings", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var settings: Settings? = null

    class Settings {

        @JsonProperty(value = "status", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var status: Board.Settings.Status? = null

    }

    @JsonProperty(value = "title", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var title: String? = null

    @JsonProperty(value = "entity_children", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var childEntities: ChildEntities? = null

    class ChildEntities {

        @JsonProperty(value = "stages", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var stages: List<BoardStageResponseJson>? = null

    }

}