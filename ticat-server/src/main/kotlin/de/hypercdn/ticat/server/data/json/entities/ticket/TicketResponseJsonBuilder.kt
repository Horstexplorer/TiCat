package de.hypercdn.ticat.server.data.json.entities.ticket

import de.hypercdn.ticat.server.data.helper.EntityTemplateBuilder
import de.hypercdn.ticat.server.data.json.entities.board.BoardResponseJson
import de.hypercdn.ticat.server.data.json.entities.board.BoardResponseJsonBuilder
import de.hypercdn.ticat.server.data.json.entities.board.builder
import de.hypercdn.ticat.server.data.json.entities.board.stage.BoardStageResponseJson
import de.hypercdn.ticat.server.data.json.entities.board.stage.BoardStageResponseJsonBuilder
import de.hypercdn.ticat.server.data.json.entities.board.stage.builder
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJson
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJsonBuilder
import de.hypercdn.ticat.server.data.json.entities.user.builder
import de.hypercdn.ticat.server.data.sql.entities.board.Board
import de.hypercdn.ticat.server.data.sql.entities.board.stage.BoardStage
import de.hypercdn.ticat.server.data.sql.entities.ticket.Ticket
import de.hypercdn.ticat.server.data.sql.entities.user.User

class TicketResponseJsonBuilder(
    private val ticket: Ticket? = null
): EntityTemplateBuilder<TicketResponseJsonBuilder, TicketResponseJson>({ TicketResponseJson() }) {

    fun includeUUID(skip: Boolean = false): TicketResponseJsonBuilder = modify(skip) {
        it.uuid = ticket?.uuid
    }

    fun includeBoard(skip: Boolean = false, board: Board? = ticket?.board, configurator: (BoardResponseJsonBuilder) -> Unit): TicketResponseJsonBuilder = modify(skip) {
        it.board = BoardResponseJson.builder(board)
            .apply(configurator)
            .build()
    }

    fun includeSeriesId(skip: Boolean = false): TicketResponseJsonBuilder = modify(skip) {
        it.seriesId = ticket?.seriesId
    }

    fun includeVersionTimestamp(skip: Boolean = false): TicketResponseJsonBuilder = modify(skip) {
        it.versionTimestamp = ticket?.modifiedAt
    }

    fun includeCreator(skip: Boolean = false, user: User? = ticket?.creator, configurator: (UserResponseJsonBuilder) -> Unit): TicketResponseJsonBuilder = modify(skip) {
        it.creator = UserResponseJson.builder(user)
            .apply(configurator)
            .build()
    }

    fun includeEditor(skip: Boolean = false, user: User? = ticket?.editor, configurator: (UserResponseJsonBuilder) -> Unit): TicketResponseJsonBuilder = modify(skip) {
        it.editor = UserResponseJson.builder(user)
            .apply(configurator)
            .build()
    }

    fun includeSettings(skip: Boolean = false): TicketResponseJsonBuilder = modify(skip) {
        it.settings = TicketResponseJson.Settings().apply {
            status = ticket?.settings?.status
            boardStageUUID = ticket?.settings?.stageUUID
            assigneeUUID = ticket?.settings?.assigneeUUID
        }
    }

    fun includeTitle(skip: Boolean = false): TicketResponseJsonBuilder = modify(skip) {
        it.title = ticket?.title
    }

    fun includeContent(skip: Boolean = false): TicketResponseJsonBuilder = modify(skip) {
        it.content = ticket?.content
    }

}

fun TicketResponseJson.Companion.builder(ticket: Ticket? = null): TicketResponseJsonBuilder = TicketResponseJsonBuilder(ticket)
fun Ticket.toJsonResponseBuilder(): TicketResponseJsonBuilder = TicketResponseJsonBuilder(this)