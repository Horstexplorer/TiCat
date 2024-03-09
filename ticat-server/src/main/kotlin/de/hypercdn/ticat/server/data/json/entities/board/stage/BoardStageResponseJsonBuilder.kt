package de.hypercdn.ticat.server.data.json.entities.board.stage

import de.hypercdn.ticat.server.helper.EntityTemplateBuilder
import de.hypercdn.ticat.server.data.json.entities.board.BoardResponseJson
import de.hypercdn.ticat.server.data.json.entities.board.BoardResponseJsonBuilder
import de.hypercdn.ticat.server.data.json.entities.board.builder
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJson
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJsonBuilder
import de.hypercdn.ticat.server.data.json.entities.user.builder
import de.hypercdn.ticat.server.data.sql.entities.board.Board
import de.hypercdn.ticat.server.data.sql.entities.board.stage.BoardStage
import de.hypercdn.ticat.server.data.sql.entities.user.User

class BoardStageResponseJsonBuilder(
    private val boardStage: BoardStage? = null
) : EntityTemplateBuilder<BoardStageResponseJsonBuilder, BoardStageResponseJson>({ BoardStageResponseJson() }){

    fun includeUUID(skip: Boolean = false): BoardStageResponseJsonBuilder = modify(skip) {
        it.uuid = boardStage?.uuid
    }

    fun includeBoard(skip: Boolean = false, board: Board? = boardStage?.board, configurator: (BoardResponseJsonBuilder) -> Unit): BoardStageResponseJsonBuilder = modify(skip) {
        it.board = BoardResponseJson.builder(board)
            .apply(configurator)
            .build()
    }

    fun includeVersionTimestamp(skip: Boolean = false): BoardStageResponseJsonBuilder = modify(skip) {
        it.versionTimestamp = boardStage?.modifiedAt
    }

    fun includeCreator(skip: Boolean = false, user: User? = boardStage?.creator, configurator: (UserResponseJsonBuilder) -> Unit): BoardStageResponseJsonBuilder = modify(skip) {
        it.creator = UserResponseJson.builder(user)
            .apply(configurator)
            .build()
    }

    fun includeEditor(skip: Boolean = false, user: User? = boardStage?.creator, configurator: (UserResponseJsonBuilder) -> Unit): BoardStageResponseJsonBuilder = modify(skip) {
        it.editor = UserResponseJson.builder(user)
            .apply(configurator)
            .build()
    }

    fun includeSettings(skip: Boolean = false): BoardStageResponseJsonBuilder = modify(skip) {
        it.settings = BoardStageResponseJson.Settings().apply {
            indexValue = boardStage?.settings?.indexValue
            status = boardStage?.settings?.status
        }
    }

    fun includeTitle(skip: Boolean = false): BoardStageResponseJsonBuilder = modify(skip) {
        it.title = boardStage?.title
    }

}

fun BoardStageResponseJson.Companion.builder(boardStage: BoardStage? = null): BoardStageResponseJsonBuilder = BoardStageResponseJsonBuilder(boardStage)
fun BoardStage.toJsonResponseBuilder(): BoardStageResponseJsonBuilder = BoardStageResponseJsonBuilder(this)