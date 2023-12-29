package de.hypercdn.ticat.server.data.json.entities.page.history

import de.hypercdn.ticat.server.data.helper.EntityTemplateBuilder
import de.hypercdn.ticat.server.data.json.entities.page.PageResponseJson
import de.hypercdn.ticat.server.data.json.entities.page.PageResponseJsonBuilder
import de.hypercdn.ticat.server.data.json.entities.page.builder
import de.hypercdn.ticat.server.data.sql.entities.page.Page
import de.hypercdn.ticat.server.data.sql.entities.page.history.PageHistory

class PageHistoryResponseJsonBuilder(
    private val pageHistory: PageHistory? = null
): EntityTemplateBuilder<PageHistoryResponseJsonBuilder, PageHistoryResponseJson>({ PageHistoryResponseJson() }) {

    fun includeUUID(skip: Boolean = false): PageHistoryResponseJsonBuilder = modify(skip) {
        it.uuid = pageHistory?.uuid
    }

    fun includePage(skip: Boolean = false, page: Page? = pageHistory?.page, configurator: (PageResponseJsonBuilder) -> Unit): PageHistoryResponseJsonBuilder = modify(skip) {
        it.page = PageResponseJson.builder(page)
            .apply(configurator)
            .build()
    }

    fun includeVersion(skip: Boolean = false): PageHistoryResponseJsonBuilder = modify(skip) {
        it.version = pageHistory?.versionId
    }

    fun includeVersionTimestamp(skip: Boolean = false): PageHistoryResponseJsonBuilder = modify(skip) {
        it.versionTimestamp = pageHistory?.createdAt
    }

    fun includeOldValues(skip: Boolean = false): PageHistoryResponseJsonBuilder = modify(skip) {
        it.oldTitle = pageHistory?.oldTitle
        it.oldContent = pageHistory?.oldContent
        it.oldSettingStatus = pageHistory?.oldSettingStatus
        it.oldSettingParentPageUUID = pageHistory?.oldSettingParentPageUUID
    }

}

fun PageHistoryResponseJson.Companion.builder(pageHistory: PageHistory? = null): PageHistoryResponseJsonBuilder = PageHistoryResponseJsonBuilder(pageHistory)
fun PageHistory.toJsonResponseBuilder(): PageHistoryResponseJsonBuilder = PageHistoryResponseJsonBuilder(this)