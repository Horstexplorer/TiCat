package de.hypercdn.ticat.server.data.json.entities.workspace

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import de.hypercdn.ticat.server.data.json.entities.board.BoardResponseJson
import de.hypercdn.ticat.server.data.json.entities.page.PageResponseJson
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJson
import de.hypercdn.ticat.server.data.json.entities.workspace.history.WorkspaceHistoryResponseJson
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import de.hypercdn.ticat.server.data.sql.entities.workspace.history.WorkspaceHistory
import java.time.OffsetDateTime
import java.util.*

class WorkspaceResponseJson {

    companion object

    @JsonProperty(value = "uuid", required = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var uuid: UUID? = null

    @JsonProperty(value = "human_id", required = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var hid: String? = null

    @JsonProperty(value = "version_timestamp", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var versionTimestamp: OffsetDateTime? = null

    @JsonProperty(value = "creator", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var creator: UserResponseJson? = null

    @JsonProperty(value = "editor", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var editor: UserResponseJson? = null

    @JsonProperty(value = "editor", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var settings: Settings? = null

    class Settings {

        @JsonProperty(value = "setting_visibility", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var visibility: Workspace.Settings.Visibility? = null

        @JsonProperty(value = "setting_access_mode", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var accessMode: Workspace.Settings.AccessMode? = null

        @JsonProperty(value = "setting_status", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var status: Workspace.Settings.Status? = null

        @JsonProperty(value = "setting_locale", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var locale: Locale? = null

    }

    @JsonProperty(value = "title", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var title: String? = null

    @JsonProperty(value = "description", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var description: String? = null

    @JsonProperty(value = "entity_children", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var childEntities: ChildEntities? = null

    class ChildEntities {

        @JsonProperty(value = "boards", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var boards: List<BoardResponseJson>? = null

        @JsonProperty(value = "pages", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var pages: List<PageResponseJson>? = null

    }

    @JsonProperty(value = "entity_history", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var history: List<WorkspaceHistoryResponseJson>? = null

}