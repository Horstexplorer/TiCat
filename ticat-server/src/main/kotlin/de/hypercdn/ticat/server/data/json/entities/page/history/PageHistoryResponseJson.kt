package de.hypercdn.ticat.server.data.json.entities.page.history

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import de.hypercdn.ticat.server.data.json.entities.page.PageResponseJson
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJson
import de.hypercdn.ticat.server.data.sql.entities.page.Page
import java.time.OffsetDateTime
import java.util.*

class PageHistoryResponseJson {

    companion object

    @JsonProperty(value = "uuid", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var uuid: UUID? = null

    @JsonProperty(value = "page", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var page: PageResponseJson? = null

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

    @JsonProperty(value = "old_setting_status", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var oldSettingStatus: Page.Settings.Status? = null

    @JsonProperty(value = "old_setting_parent_page_uuid", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var oldSettingParentPageUUID: UUID? = null

}