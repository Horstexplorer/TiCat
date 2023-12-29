package de.hypercdn.ticat.server.data.json.entities.messages.history

import de.hypercdn.ticat.server.data.helper.EntityTemplateBuilder
import de.hypercdn.ticat.server.data.json.entities.messages.MessageResponseJson
import de.hypercdn.ticat.server.data.json.entities.messages.MessageResponseJsonBuilder
import de.hypercdn.ticat.server.data.json.entities.messages.builder
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJson
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJsonBuilder
import de.hypercdn.ticat.server.data.json.entities.user.builder
import de.hypercdn.ticat.server.data.sql.entities.messages.Message
import de.hypercdn.ticat.server.data.sql.entities.messages.history.MessageHistory
import de.hypercdn.ticat.server.data.sql.entities.user.User

class MessageHistoryResponseJsonBuilder(
    private val messageHistory: MessageHistory? = null
): EntityTemplateBuilder<MessageHistoryResponseJsonBuilder, MessageHistoryResponseJson>({ MessageHistoryResponseJson() }) {

    fun includeUUID(skip: Boolean = false): MessageHistoryResponseJsonBuilder = modify(skip) {
        it.uuid = messageHistory?.uuid
    }

    fun includeMessage(skip: Boolean = false, message: Message? = messageHistory?.message, configurator: (MessageResponseJsonBuilder) -> Unit): MessageHistoryResponseJsonBuilder = modify(skip) {
        it.message = MessageResponseJson.builder(message)
            .apply(configurator)
            .build()
    }

    fun includeVersion(skip: Boolean = false): MessageHistoryResponseJsonBuilder = modify(skip) {
        it.version = messageHistory?.versionId
    }

    fun includeVersionTimestamp(skip: Boolean = false): MessageHistoryResponseJsonBuilder = modify(skip) {
        it.versionTimestamp = messageHistory?.createdAt
    }

    fun includeEditor(skip: Boolean = false, user: User? = messageHistory?.editor, configurator: (UserResponseJsonBuilder) -> Unit): MessageHistoryResponseJsonBuilder = modify(skip) {
        it.editor = UserResponseJson.builder(user)
            .apply(configurator)
            .build()
    }

    fun includeOldValues(skip: Boolean = false): MessageHistoryResponseJsonBuilder = modify(skip) {
        it.oldContent = messageHistory?.oldContent
        it.oldSettingStatus = messageHistory?.oldSettingStatus
    }

}

fun MessageHistoryResponseJson.Companion.builder(messageHistory: MessageHistory? = null): MessageHistoryResponseJsonBuilder = MessageHistoryResponseJsonBuilder(messageHistory)
fun MessageHistory.toJsonResponseBuilder(): MessageHistoryResponseJsonBuilder = MessageHistoryResponseJsonBuilder(this)