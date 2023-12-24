package de.hypercdn.ticat.server.data.json.entities.messages

import de.hypercdn.ticat.server.data.helper.EntityTemplateBuilder
import de.hypercdn.ticat.server.data.sql.entities.messages.Message

class MessageResponseJsonBuilder(
    private val message: Message? = null
): EntityTemplateBuilder<MessageResponseJson>({ MessageResponseJson() }) {

}

fun MessageResponseJson.builder(message: Message? = null): MessageResponseJsonBuilder = MessageResponseJsonBuilder(message)
fun Message.toJsonResponseBuilder(): MessageResponseJsonBuilder = MessageResponseJsonBuilder(this)