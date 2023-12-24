package de.hypercdn.ticat.server.data.json.entities.page

import de.hypercdn.ticat.server.data.helper.EntityTemplateBuilder
import de.hypercdn.ticat.server.data.sql.entities.page.Page

class PageResponseJsonBuilder(
    private var page: Page? = null
): EntityTemplateBuilder<PageResponseJson>({ PageResponseJson() }) {

}

fun PageResponseJson.Companion.builder(page: Page? = null): PageResponseJsonBuilder = PageResponseJsonBuilder(page)
fun Page.toJsonResponseBuilder(): PageResponseJsonBuilder = PageResponseJsonBuilder(this)