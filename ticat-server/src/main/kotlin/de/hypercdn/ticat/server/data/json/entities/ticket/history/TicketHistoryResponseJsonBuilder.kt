package de.hypercdn.ticat.server.data.json.entities.ticket.history

import de.hypercdn.ticat.server.data.helper.EntityTemplateBuilder
import de.hypercdn.ticat.server.data.json.entities.ticket.TicketResponseJson
import de.hypercdn.ticat.server.data.sql.entities.ticket.history.TicketHistory

class TicketHistoryResponseJsonBuilder(
    private val ticketHistory: TicketHistory? = null
): EntityTemplateBuilder<TicketHistoryResponseJson>({ TicketHistoryResponseJson() }) {

}

fun TicketHistoryResponseJson.builder(ticketHistory: TicketHistory? = null): TicketHistoryResponseJsonBuilder = TicketHistoryResponseJsonBuilder(ticketHistory)
fun TicketHistory.toJsonResponseBuilder(): TicketHistoryResponseJsonBuilder = TicketHistoryResponseJsonBuilder(this)