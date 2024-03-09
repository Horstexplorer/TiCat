package de.hypercdn.ticat.server.data.json.entities.board

import de.hypercdn.ticat.server.helper.EntityTemplateBuilder
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJson
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJsonBuilder
import de.hypercdn.ticat.server.data.json.entities.user.builder
import de.hypercdn.ticat.server.data.json.entities.workspace.WorkspaceResponseJson
import de.hypercdn.ticat.server.data.json.entities.workspace.WorkspaceResponseJsonBuilder
import de.hypercdn.ticat.server.data.json.entities.workspace.builder
import de.hypercdn.ticat.server.data.sql.entities.board.Board
import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace

class BoardResponseJsonBuilder(
    private val board: Board? = null
) : EntityTemplateBuilder<BoardResponseJsonBuilder, BoardResponseJson>({ BoardResponseJson() }) {

    fun includeUUID(skip: Boolean = false): BoardResponseJsonBuilder = modify(skip) {
        it.uuid = board?.uuid
    }

    fun includeWorkspace(skip: Boolean = false, workspace: Workspace? = board?.workspace, configurator: (WorkspaceResponseJsonBuilder) -> Unit): BoardResponseJsonBuilder = modify(skip) {
        it.workspace = WorkspaceResponseJson.builder(workspace)
            .apply(configurator)
            .build()
    }

    fun includeVersionTimestamp(skip: Boolean = false): BoardResponseJsonBuilder = modify(skip) {
        it.versionTimestamp = board?.modifiedAt
    }

    fun includeCreator(skip: Boolean = false, user: User? = board?.creator, configurator: (UserResponseJsonBuilder) -> Unit): BoardResponseJsonBuilder = modify(skip) {
        it.creator = UserResponseJson.builder(user)
            .apply(configurator)
            .build()
    }

    fun includeEditor(skip: Boolean = false, user: User? = board?.creator, configurator: (UserResponseJsonBuilder) -> Unit): BoardResponseJsonBuilder = modify(skip) {
        it.editor = UserResponseJson.builder(user)
            .apply(configurator)
            .build()
    }

    fun includeSettings(skip: Boolean = false): BoardResponseJsonBuilder = modify(skip) {
        it.settings = BoardResponseJson.Settings().apply {
            status = board?.settings?.status
        }
    }

    fun includeTitle(skip: Boolean = false): BoardResponseJsonBuilder = modify(skip) {
        it.title = board?.title
    }

}

fun BoardResponseJson.Companion.builder(board: Board? = null): BoardResponseJsonBuilder = BoardResponseJsonBuilder(board)
fun Board.toJsonResponseBuilder(): BoardResponseJsonBuilder = BoardResponseJsonBuilder(this)