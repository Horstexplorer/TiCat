package de.hypercdn.ticat.server.data.json.entities.page.history

import de.hypercdn.ticat.server.data.helper.EntityTemplateBuilder
import de.hypercdn.ticat.server.data.sql.entities.page.history.PageHistory

class PageHistoryResponseJsonBuilder(
    private val pageHistory: PageHistory? = null
): EntityTemplateBuilder<PageHistoryResponseJson>({ PageHistoryResponseJson() }) {

}

fun PageHistoryResponseJson.Companion.builder(pageHistory: PageHistory? = null): PageHistoryResponseJsonBuilder = PageHistoryResponseJsonBuilder(pageHistory)
fun PageHistory.toJsonResponseBuilder(): PageHistoryResponseJsonBuilder = PageHistoryResponseJsonBuilder(this)