package de.hypercdn.ticat.server.data.json.entities.messages.history

import de.hypercdn.ticat.server.data.helper.EntityTemplateBuilder
import de.hypercdn.ticat.server.data.sql.entities.messages.history.MessageHistory

class MessageHistoryResponseJsonBuilder(
    private val messageHistory: MessageHistory? = null
): EntityTemplateBuilder<MessageHistoryResponseJson>({ MessageHistoryResponseJson() }) {

}

fun MessageHistoryResponseJson.Companion.builder(messageHistory: MessageHistory? = null): MessageHistoryResponseJsonBuilder = MessageHistoryResponseJsonBuilder(messageHistory)
fun MessageHistory.toJsonResponseBuilder(): MessageHistoryResponseJsonBuilder = MessageHistoryResponseJsonBuilder(this)