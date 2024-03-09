package de.hypercdn.ticat.server.data.json.entities.page

import de.hypercdn.ticat.server.helper.EntityTemplateBuilder
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJson
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJsonBuilder
import de.hypercdn.ticat.server.data.json.entities.user.builder
import de.hypercdn.ticat.server.data.json.entities.workspace.WorkspaceResponseJson
import de.hypercdn.ticat.server.data.json.entities.workspace.WorkspaceResponseJsonBuilder
import de.hypercdn.ticat.server.data.json.entities.workspace.builder
import de.hypercdn.ticat.server.data.sql.entities.page.Page
import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace

class PageResponseJsonBuilder(
    private var page: Page? = null
): EntityTemplateBuilder<PageResponseJsonBuilder, PageResponseJson>({ PageResponseJson() }) {

    fun includeUUID(skip: Boolean = false): PageResponseJsonBuilder = modify(skip) {
        it.uuid = page?.uuid
    }

    fun includeWorkspace(skip: Boolean = false, workspace: Workspace? = page?.workspace, configurator: (WorkspaceResponseJsonBuilder) -> Unit): PageResponseJsonBuilder = modify(skip) {
        it.workspace = WorkspaceResponseJson.builder(workspace)
            .apply(configurator)
            .build()
    }

    fun includeVersionTimestamp(skip: Boolean = false): PageResponseJsonBuilder = modify(skip) {
        it.versionTimestamp = page?.modifiedAt
    }

    fun includeCreator(skip: Boolean = false, user: User? = page?.creator, configurator: (UserResponseJsonBuilder) -> Unit): PageResponseJsonBuilder = modify(skip) {
        it.creator = UserResponseJson.builder(user)
            .apply(configurator)
            .build()
    }

    fun includeEditor(skip: Boolean = false, user: User? = page?.editor, configurator: (UserResponseJsonBuilder) -> Unit): PageResponseJsonBuilder = modify(skip) {
        it.editor = UserResponseJson.builder(user)
            .apply(configurator)
            .build()
    }

    fun includeSettings(skip: Boolean = false): PageResponseJsonBuilder = modify(skip) {
        it.settings = PageResponseJson.Settings().apply {
            status = page?.settings?.status
            parentPageUUID = page?.settings?.parentPageUUID
        }
    }

    fun includeTitle(skip: Boolean = false): PageResponseJsonBuilder = modify(skip) {
        it.title = page?.title
    }

    fun includeContent(skip: Boolean = false): PageResponseJsonBuilder = modify(skip) {
        it.content = page?.content
    }

}

fun PageResponseJson.Companion.builder(page: Page? = null): PageResponseJsonBuilder = PageResponseJsonBuilder(page)
fun Page.toJsonResponseBuilder(): PageResponseJsonBuilder = PageResponseJsonBuilder(this)