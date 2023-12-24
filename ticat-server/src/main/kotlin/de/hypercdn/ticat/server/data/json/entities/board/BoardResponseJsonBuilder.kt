package de.hypercdn.ticat.server.data.json.entities.board

import de.hypercdn.ticat.server.data.helper.EntityTemplateBuilder
import de.hypercdn.ticat.server.data.sql.entities.board.Board

class BoardResponseJsonBuilder(
    private val board: Board? = null
) : EntityTemplateBuilder<BoardResponseJson>({ BoardResponseJson() }) {

}

fun BoardResponseJson.Companion.builder(board: Board? = null): BoardResponseJsonBuilder = BoardResponseJsonBuilder(board)
fun Board.toJsonResponseBuilder(): BoardResponseJsonBuilder = BoardResponseJsonBuilder(this)