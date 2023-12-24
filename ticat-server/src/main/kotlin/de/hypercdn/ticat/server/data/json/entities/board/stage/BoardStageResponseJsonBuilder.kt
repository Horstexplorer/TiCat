package de.hypercdn.ticat.server.data.json.entities.board.stage

import de.hypercdn.ticat.server.data.helper.EntityTemplateBuilder
import de.hypercdn.ticat.server.data.sql.entities.board.stage.BoardStage

class BoardStageResponseJsonBuilder(
    private val boardStage: BoardStage? = null
) : EntityTemplateBuilder<BoardStageResponseJson>({ BoardStageResponseJson() }){

}

fun BoardStageResponseJson.Companion.builder(boardStage: BoardStage? = null): BoardStageResponseJsonBuilder = BoardStageResponseJsonBuilder(boardStage)
fun BoardStage.toJsonResponseBuilder(): BoardStageResponseJsonBuilder = BoardStageResponseJsonBuilder(this)