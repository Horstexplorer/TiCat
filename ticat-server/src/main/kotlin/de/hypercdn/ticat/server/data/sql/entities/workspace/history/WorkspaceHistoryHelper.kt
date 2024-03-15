package de.hypercdn.ticat.server.data.sql.entities.workspace.history

import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import de.hypercdn.ticat.server.helper.ModificationContext
import java.util.function.BiFunction

fun ModificationContext<Workspace>.asHistoryFromOriginal(): WorkspaceHistory = asHistory({ l, _ -> l }, { _, r -> r })

fun ModificationContext<Workspace>.asHistoryFromSnapshot(): WorkspaceHistory = asHistory({ _, r -> r }, { l, _ -> l })

fun ModificationContext<Workspace>.asHistory(mappingLeft: BiFunction<Workspace, Workspace, Workspace> = BiFunction { l, _ -> l }, mappingRight: BiFunction<Workspace, Workspace, Workspace> = BiFunction { _, r -> r }): WorkspaceHistory {
    val difference = getAbsoluteDifference(mappingLeft, mappingRight).difference.entriesDiffering()
    return WorkspaceHistory()
        .apply {
            entityReferenceUUID = original().uuid

            difference["title"]?.also {
                oldTitle = it.leftValue() as String
            }
            difference["description"]?.also {
                oldDescription = it.leftValue() as String
            }
            difference["settings"]?.also { setting ->
                val inner = setting.leftValue() as Map<*,*>
                inner["status"]?.also {
                    oldStatus = Workspace.Settings.Status.valueOf(it as String)
                }
                inner["accessMode"]?.also {
                    oldAccessMode = Workspace.Settings.AccessMode.valueOf(it as String)
                }
                inner["visibility"]?.also {
                    oldVisibility = Workspace.Settings.Visibility.valueOf(it as String)
                }
            }
        }
}

fun Workspace.restoreTo(history: WorkspaceHistory): Workspace = apply {
    history.oldTitle?.also {
        title = it
    }
    history.oldDescription?.also {
        description = it
    }
    history.oldStatus?.also {
        settings.status = it
    }
    history.oldAccessMode?.also {
        settings.accessMode = it
    }
    history.oldVisibility?.also {
        settings.visibility = it
    }
}

fun Workspace.restoreInOrder(history: List<WorkspaceHistory>): Workspace = apply {
    history.forEach { restoreTo(it) }
}