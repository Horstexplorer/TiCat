package de.hypercdn.ticat.server.data.json.entities.workspace

import de.hypercdn.ticat.server.data.helper.EntityTemplateBuilder
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJson
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJsonBuilder
import de.hypercdn.ticat.server.data.json.entities.user.builder
import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace

class WorkspaceResponseJsonBuilder(
    private val workspace: Workspace? = null
): EntityTemplateBuilder<WorkspaceResponseJsonBuilder, WorkspaceResponseJson>({ WorkspaceResponseJson() }) {

    fun includeUUID(skip: Boolean = false): WorkspaceResponseJsonBuilder = modify(skip) {
        it.uuid = workspace?.uuid
    }

    fun includeHumanId(skip: Boolean = false): WorkspaceResponseJsonBuilder = modify(skip) {
        it.hid = workspace?.humanId
    }

    fun includeVersionTimestamp(skip: Boolean = false): WorkspaceResponseJsonBuilder = modify(skip) {
        it.versionTimestamp = workspace?.modifiedAt
    }

    fun includeCreator(skip: Boolean = false, user: User? = workspace?.creator, configurator: (UserResponseJsonBuilder) -> Unit): WorkspaceResponseJsonBuilder = modify(skip) {
        it.creator = UserResponseJson.builder(user)
            .apply(configurator)
            .build()
    }

    fun includeEditor(skip: Boolean = false, user: User? = workspace?.editor, configurator: (UserResponseJsonBuilder) -> Unit): WorkspaceResponseJsonBuilder = modify(skip) {
        it.editor = UserResponseJson.builder(user)
            .apply(configurator)
            .build()
    }

    fun includeSettings(skip: Boolean = false): WorkspaceResponseJsonBuilder = modify(skip) {
        it.settings = WorkspaceResponseJson.Settings().apply {
            visibility = workspace?.settings?.visibility
            accessMode = workspace?.settings?.accessMode
            status = workspace?.settings?.status
        }
    }

    fun includeTitle(skip: Boolean = false): WorkspaceResponseJsonBuilder = modify(skip) {
        it.title = workspace?.title
    }

    fun includeDescription(skip: Boolean = false): WorkspaceResponseJsonBuilder = modify(skip) {
        it.description = workspace?.description
    }

}

fun WorkspaceResponseJson.Companion.builder(workspace: Workspace? = null): WorkspaceResponseJsonBuilder = WorkspaceResponseJsonBuilder(workspace)
fun Workspace.toJsonResponseBuilder(): WorkspaceResponseJsonBuilder = WorkspaceResponseJsonBuilder(this)