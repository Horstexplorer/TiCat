package de.hypercdn.ticat.server.data.json.entities.workspace.history

import de.hypercdn.ticat.server.data.helper.EntityTemplateBuilder
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJson
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJsonBuilder
import de.hypercdn.ticat.server.data.json.entities.user.builder
import de.hypercdn.ticat.server.data.json.entities.workspace.WorkspaceResponseJson
import de.hypercdn.ticat.server.data.json.entities.workspace.WorkspaceResponseJsonBuilder
import de.hypercdn.ticat.server.data.json.entities.workspace.builder
import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import de.hypercdn.ticat.server.data.sql.entities.workspace.history.WorkspaceHistory

class WorkspaceHistoryResponseJsonBuilder(
    private val history: WorkspaceHistory? = null
): EntityTemplateBuilder<WorkspaceHistoryResponseJsonBuilder, WorkspaceHistoryResponseJson>({ WorkspaceHistoryResponseJson() }) {

    fun includeUUID(skip: Boolean = false) : WorkspaceHistoryResponseJsonBuilder  = modify(skip) {
        it.uuid = history?.uuid
    }

    fun includeWorkspace(skip: Boolean = false, workspace: Workspace? = history?.workspace, configurator: (WorkspaceResponseJsonBuilder) -> Unit) : WorkspaceHistoryResponseJsonBuilder  = modify(skip) {
        it.workspace = WorkspaceResponseJson.builder(workspace)
            .apply(configurator)
            .build()
    }

    fun includeVersion(skip: Boolean = false) : WorkspaceHistoryResponseJsonBuilder  = modify(skip) {
        it.version = history?.versionId
    }

    fun includeVersionTimestamp(skip: Boolean = false) : WorkspaceHistoryResponseJsonBuilder  = modify(skip) {
        it.versionTimestamp = history?.createdAt
    }

    fun includeEditor(skip: Boolean = false, user: User? = history?.editor, configurator: (UserResponseJsonBuilder) -> Unit) : WorkspaceHistoryResponseJsonBuilder  = modify(skip) {
        it.editor = UserResponseJson.builder(user)
            .apply(configurator)
            .build()
    }

    fun includeOldValues(skip: Boolean = false) : WorkspaceHistoryResponseJsonBuilder  = modify(skip) {
        it.oldTitle = history?.oldTitle
        it.oldDescription = history?.oldDescription
        it.oldVisibility = history?.oldVisibility
        it.oldAccessMode = history?.oldAccessMode
        it.oldStatus = history?.oldStatus
    }

}

fun WorkspaceHistoryResponseJson.Companion.builder(history: WorkspaceHistory? = null): WorkspaceHistoryResponseJsonBuilder = WorkspaceHistoryResponseJsonBuilder(history)
fun WorkspaceHistory.toJsonResponseBuilder(): WorkspaceHistoryResponseJsonBuilder = WorkspaceHistoryResponseJsonBuilder(this)