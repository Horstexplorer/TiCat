package de.hypercdn.ticat.server.data.sql.entities.message.history

import de.hypercdn.ticat.server.data.sql.entities.message.Message
import de.hypercdn.ticat.server.helper.ModificationContext
import java.util.function.BiFunction


fun ModificationContext<Message>.asHistoryFromOriginal(): MessageHistory = asHistory({ l, _ -> l }, { _, r -> r })

fun ModificationContext<Message>.asHistoryFromSnapshot(): MessageHistory = asHistory({ _, r -> r }, { l, _ -> l })

fun ModificationContext<Message>.asHistory(mappingLeft: BiFunction<Message, Message, Message> = BiFunction { l, _ -> l }, mappingRight: BiFunction<Message, Message, Message> = BiFunction { _, r -> r }): MessageHistory {
    val difference = getAbsoluteDifference(mappingLeft, mappingRight).difference.entriesDiffering()
    return MessageHistory()
        .apply {
            entityReferenceUUID = original().uuid

            difference["content"]?.also {
                oldContent = it.leftValue() as String
            }
            difference["settings"]?.also { setting ->
                val inner = setting.leftValue() as Map<*,*>
                inner["status"]?.also {
                    oldSettingStatus = Message.Settings.Status.valueOf(it as String)
                }
            }
        }
}

fun Message.restoreTo(history: MessageHistory): Message = apply {
    history.oldContent?.also {
        content = it
    }
    history.oldSettingStatus?.also {
        settings.status = it
    }
}

fun Message.restoreInOrder(history: List<MessageHistory>): Message = apply {
    history.forEach { restoreTo(it) }
}