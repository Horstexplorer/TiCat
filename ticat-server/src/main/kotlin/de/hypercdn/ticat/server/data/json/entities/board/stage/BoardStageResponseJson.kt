package de.hypercdn.ticat.server.data.json.entities.board.stage

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import de.hypercdn.ticat.server.data.json.entities.board.BoardResponseJson
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJson
import de.hypercdn.ticat.server.data.sql.entities.board.stage.BoardStage
import java.time.OffsetDateTime
import java.util.*

class BoardStageResponseJson {

    companion object

    @JsonProperty(value = "uuid", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var uuid: UUID? = null

    @JsonProperty(value = "board", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var board: BoardResponseJson? = null

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

        @JsonProperty(value = "setting_index_value", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var indexValue: Int? = null

        @JsonProperty(value = "setting_status", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var status: BoardStage.Settings.Status? = null

    }

    @JsonProperty(value = "title", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var title: String? = null

}