package de.hypercdn.ticat.server.data.json.entities.workspace.history

import de.hypercdn.ticat.server.data.helper.EntityTemplateBuilder
import de.hypercdn.ticat.server.data.sql.entities.workspace.history.WorkspaceHistory

class WorkspaceHistoryJsonBuilder(
    private val history: WorkspaceHistory? = null
): EntityTemplateBuilder<WorkspaceHistoryJson>({ WorkspaceHistoryJson() }) {

}

fun WorkspaceHistoryJson.Companion.builder(history: WorkspaceHistory? = null): WorkspaceHistoryJsonBuilder = WorkspaceHistoryJsonBuilder(history)
fun WorkspaceHistory.toJsonResponseBuilder(): WorkspaceHistoryJsonBuilder = WorkspaceHistoryJsonBuilder(this)