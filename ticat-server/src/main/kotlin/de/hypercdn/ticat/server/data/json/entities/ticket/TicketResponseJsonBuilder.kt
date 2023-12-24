package de.hypercdn.ticat.server.data.json.entities.ticket

import de.hypercdn.ticat.server.data.helper.EntityTemplateBuilder
import de.hypercdn.ticat.server.data.sql.entities.ticket.Ticket

class TicketResponseJsonBuilder(
    private val ticket: Ticket? = null
): EntityTemplateBuilder<TicketResponseJson>({ TicketResponseJson() }) {

}

fun TicketResponseJson.Companion.builder(ticket: Ticket? = null): TicketResponseJsonBuilder = TicketResponseJsonBuilder(ticket)
fun Ticket.toJsonResponseBuilder(): TicketResponseJsonBuilder = TicketResponseJsonBuilder(this)