package de.hypercdn.ticat.server.data.json.entities.user

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import de.hypercdn.ticat.server.data.sql.entities.user.User
import java.time.OffsetDateTime
import java.util.*

class UserResponseJson(
    @JsonIgnore
    val user: User? = null
) {

    @JsonProperty(value = "uuid", required = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var uuid: UUID? = null

    @JsonProperty(value = "version_timestamp", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var versionTimestamp: OffsetDateTime? = null

    @JsonProperty(value = "name", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var name: Name? = null

    class Name {

        @JsonProperty(value = "first_name", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var firstName: String? = null

        @JsonProperty(value = "last_name", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var lastName: String? = null

        @JsonProperty(value = "display_name", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var displayName: String? = null

    }

    @JsonProperty(value = "email", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var email: String? = null

    @JsonProperty(value = "account_type", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var accountType: User.AccountType? = null

    @JsonProperty(value = "permissions", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var permission: Permissions? = null

    @JsonProperty(value = "effective_permissions", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var effectivePermission: Permissions? = null

    class Permissions {

        @JsonProperty(value = "permission_can_create_new_workspaces", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var canCreateNewWorkspaces: Boolean? = null

        @JsonProperty(value = "permission_can_request_workspace_access", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var canRequestWorkspaceAccess: Boolean? = null

        @JsonProperty(value = "permission_can_send_messages_to_users", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var canSendMessagesToUsers: Boolean? = null

    }

    @JsonProperty(value = "settings", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var settings: Settings? = null

    class Settings {

        @JsonProperty(value = "setting_receive_workspace_invitations_from_origin", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var receiveWorkspaceInvitationsFromOrigin: User.Settings.WorkspaceInvitationOrigin? = null

        @JsonProperty(value = "setting_receive_messages_from_origin", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var receiveMessagesFromOrigin: User.Settings.MessageOrigin? = null

        @JsonProperty(value = "setting_status", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var status: User.Settings.Status? = null

    }

}