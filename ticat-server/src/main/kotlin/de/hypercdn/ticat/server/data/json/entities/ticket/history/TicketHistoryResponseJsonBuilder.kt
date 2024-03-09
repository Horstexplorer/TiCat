package de.hypercdn.ticat.server.data.json.entities.ticket.history

import de.hypercdn.ticat.server.helper.EntityTemplateBuilder
import de.hypercdn.ticat.server.data.json.entities.ticket.TicketResponseJson
import de.hypercdn.ticat.server.data.json.entities.ticket.TicketResponseJsonBuilder
import de.hypercdn.ticat.server.data.json.entities.ticket.builder
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJson
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJsonBuilder
import de.hypercdn.ticat.server.data.json.entities.user.builder
import de.hypercdn.ticat.server.data.sql.entities.ticket.Ticket
import de.hypercdn.ticat.server.data.sql.entities.ticket.history.TicketHistory
import de.hypercdn.ticat.server.data.sql.entities.user.User

class TicketHistoryResponseJsonBuilder(
    private val ticketHistory: TicketHistory? = null
): EntityTemplateBuilder<TicketHistoryResponseJsonBuilder, TicketHistoryResponseJson>({ TicketHistoryResponseJson() }) {

    fun includeUUID(skip: Boolean = false): TicketHistoryResponseJsonBuilder = modify(skip) {
        it.uuid = ticketHistory?.uuid
    }

    fun includeTicket(skip: Boolean = false, ticket: Ticket? = ticketHistory?.entity, configurator: (TicketResponseJsonBuilder) -> Unit): TicketHistoryResponseJsonBuilder = modify(skip) {
        it.ticket = TicketResponseJson.builder(ticket)
            .apply(configurator)
            .build()
    }

    fun includeVersion(skip: Boolean = false): TicketHistoryResponseJsonBuilder = modify(skip) {
        it.version = ticketHistory?.versionId
    }

    fun includeVersionTimestamp(skip: Boolean = false): TicketHistoryResponseJsonBuilder = modify(skip) {
        it.versionTimestamp = ticketHistory?.createdAt
    }

    fun includeEditor(skip: Boolean = false, user: User? = ticketHistory?.editor, configurator: (UserResponseJsonBuilder) -> Unit): TicketHistoryResponseJsonBuilder = modify(skip) {
        it.editor = UserResponseJson.builder(user)
            .apply(configurator)
            .build()
    }

    fun includeOldValues(skip: Boolean = false): TicketHistoryResponseJsonBuilder = modify(skip) {
        it.oldTitle = ticketHistory?.oldTitle
        it.oldContent = ticketHistory?.oldContent
        it.oldStatus = ticketHistory?.oldStatus
        it.oldBoardStageUUID = ticketHistory?.oldStageUUID
        it.oldAssigneeUUID = ticketHistory?.oldAssigneeUUID
    }

}

fun TicketHistoryResponseJson.builder(ticketHistory: TicketHistory? = null): TicketHistoryResponseJsonBuilder = TicketHistoryResponseJsonBuilder(ticketHistory)
fun TicketHistory.toJsonResponseBuilder(): TicketHistoryResponseJsonBuilder = TicketHistoryResponseJsonBuilder(this)