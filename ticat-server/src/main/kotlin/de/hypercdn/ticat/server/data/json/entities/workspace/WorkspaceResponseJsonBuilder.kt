package de.hypercdn.ticat.server.data.json.entities.workspace

import de.hypercdn.ticat.server.data.helper.EntityTemplateBuilder
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace

class WorkspaceResponseJsonBuilder(
    private val workspace: Workspace? = null
): EntityTemplateBuilder<WorkspaceResponseJson>({ WorkspaceResponseJson() }) {

}

fun WorkspaceResponseJson.Companion.builder(workspace: Workspace? = null): WorkspaceResponseJsonBuilder = WorkspaceResponseJsonBuilder(workspace)
fun Workspace.toJsonResponseBuilder(): WorkspaceResponseJsonBuilder = WorkspaceResponseJsonBuilder(this)