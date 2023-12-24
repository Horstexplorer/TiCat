package de.hypercdn.ticat.server.data.json.entities.workspace.member

import de.hypercdn.ticat.server.data.helper.EntityTemplateBuilder
import de.hypercdn.ticat.server.data.sql.entities.workspace.member.WorkspaceMember

class WorkspaceMemberResponseJsonBuilder(
    private val workspaceMember: WorkspaceMember? = null
): EntityTemplateBuilder<WorkspaceMemberResponseJson>({ WorkspaceMemberResponseJson() }) {

}

fun WorkspaceMemberResponseJson.Companion.builder(workspaceMember: WorkspaceMember? = null): WorkspaceMemberResponseJsonBuilder = WorkspaceMemberResponseJsonBuilder(workspaceMember)
fun WorkspaceMember.toJsonResponseBuilder(): WorkspaceMemberResponseJsonBuilder = WorkspaceMemberResponseJsonBuilder(this)