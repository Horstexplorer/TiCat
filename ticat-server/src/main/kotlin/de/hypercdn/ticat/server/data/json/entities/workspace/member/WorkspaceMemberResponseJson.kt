package de.hypercdn.ticat.server.data.json.entities.workspace.member

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJson
import de.hypercdn.ticat.server.data.json.entities.workspace.WorkspaceResponseJson
import de.hypercdn.ticat.server.data.sql.entities.workspace.member.WorkspaceMember
import java.time.OffsetDateTime

class WorkspaceMemberResponseJson {

    companion object

    @JsonProperty(value = "user", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var user: UserResponseJson? = null

    @JsonProperty(value = "workspace", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var workspace: WorkspaceResponseJson? = null

    @JsonProperty(value = "version_timestamp", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var versionTimestamp: OffsetDateTime? = null

    @JsonProperty(value = "membership_status", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var membershipStatus: WorkspaceMember.MembershipStatus? = null

    @JsonProperty(value = "permissions", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var permissions: Permissions? = null

    @JsonProperty(value = "effective_permissions", required = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var effectivePermission: Permissions? = null

    class Permissions {

        @JsonProperty(value = "permission_pages", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var pagePermission: WorkspaceMember.Permissions.PagePermission? = null

        @JsonProperty(value = "permission_boards", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var boardPermission: WorkspaceMember.Permissions.BoardPermission? = null

        @JsonProperty(value = "permission_tickets", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var ticketPermission: WorkspaceMember.Permissions.TicketPermission? = null

        @JsonProperty(value = "permission_workspace", required = false)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var workspacePermission: WorkspaceMember.Permissions.WorkspacePermission? = null

    }

}