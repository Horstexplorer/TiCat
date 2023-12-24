package de.hypercdn.ticat.server.data.json.entities.page

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJson
import de.hypercdn.ticat.server.data.json.entities.workspace.WorkspaceResponseJson
import de.hypercdn.ticat.server.data.sql.entities.page.Page
import java.time.OffsetDateTime
import java.util.UUID

class PageResponseJson {

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

        @JsonProperty(value = "setting_status", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var status: Page.Settings.Status? = null

        @JsonProperty(value = "setting_parent_page_uuid", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var parentPageUUID: UUID? = null

    }

    @JsonProperty(value = "title", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var title: String? = null

    @JsonProperty(value = "content", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var content: String? = null

    @JsonProperty(value = "child_pages", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var childPages: List<PageResponseJson>? = null

}