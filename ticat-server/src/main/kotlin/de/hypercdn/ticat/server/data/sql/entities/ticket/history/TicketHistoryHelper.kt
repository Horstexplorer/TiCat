package de.hypercdn.ticat.server.data.sql.entities.ticket.history

import de.hypercdn.ticat.server.data.sql.entities.ticket.Ticket
import de.hypercdn.ticat.server.helper.ModificationContext
import java.util.*
import java.util.function.BiFunction

fun ModificationContext<Ticket>.asHistoryFromOriginal(): TicketHistory = asHistory({ l, _ -> l }, { _, r -> r })

fun ModificationContext<Ticket>.asHistoryFromSnapshot(): TicketHistory = asHistory({ _, r -> r }, { l, _ -> l })

fun ModificationContext<Ticket>.asHistory(mappingLeft: BiFunction<Ticket, Ticket, Ticket> = BiFunction { l, _ -> l }, mappingRight: BiFunction<Ticket, Ticket, Ticket> = BiFunction { _, r -> r }): TicketHistory {
    val difference = getAbsoluteDifference(mappingLeft, mappingRight).difference.entriesDiffering()
    return TicketHistory()
        .apply {
            entityReferenceUUID = original().uuid

            difference["title"]?.also {
                oldTitle = it.leftValue() as String
            }
            difference["content"]?.also {
                oldContent = it.leftValue() as String
            }
            difference["settings"]?.also { setting ->
                val inner = setting.leftValue() as Map<*,*>
                inner["status"]?.also {
                    oldStatus = Ticket.Settings.Status.valueOf(it as String)
                }
                inner["stageUUID"]?.also {
                    oldStageUUID = it as UUID
                }
                inner["assigneeUUID"]?.also {
                    oldAssigneeUUID = it as UUID
                }
            }
        }
}

fun Ticket.restoreTo(history: TicketHistory): Ticket = apply {
    history.oldTitle?.also {
        title = it
    }
    history.oldContent?.also {
        content = it
    }
    history.oldStatus?.also {
        settings.status = it
    }
    history.oldStageUUID?.also {
        settings.stageUUID = it
    }
    history.oldAssigneeUUID?.also {
        settings.assigneeUUID = it
    }
}

fun Ticket.restoreInOrder(history: List<TicketHistory>): Ticket = apply {
    history.forEach { restoreTo(it) }
}