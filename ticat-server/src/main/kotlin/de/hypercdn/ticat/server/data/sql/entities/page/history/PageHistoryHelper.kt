package de.hypercdn.ticat.server.data.sql.entities.page.history

import de.hypercdn.ticat.server.data.sql.entities.page.Page
import de.hypercdn.ticat.server.helper.ModificationContext
import java.util.UUID
import java.util.function.BiFunction

fun ModificationContext<Page>.asHistoryFromOriginal(): PageHistory = asHistory({ l, _ -> l }, { _, r -> r })

fun ModificationContext<Page>.asHistoryFromSnapshot(): PageHistory = asHistory({ _, r -> r }, { l, _ -> l })

fun ModificationContext<Page>.asHistory(mappingLeft: BiFunction<Page, Page, Page> = BiFunction { l, _ -> l }, mappingRight: BiFunction<Page, Page, Page> = BiFunction { _, r -> r }): PageHistory {
    val difference = getAbsoluteDifference(mappingLeft, mappingRight).difference.entriesDiffering()
    return PageHistory()
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
                    oldSettingStatus = Page.Settings.Status.valueOf(it as String)
                }
                inner["parentPageUUID"]?.also {
                    oldSettingParentPageUUID = it as UUID
                }
            }
        }
}

fun Page.restoreTo(history: PageHistory): Page = apply {
    history.oldTitle?.also {
        title = it
    }
    history.oldContent?.also {
        content = it
    }
    history.oldSettingStatus?.also {
        settings.status = it
    }
    history.oldSettingParentPageUUID?.also {
        settings.parentPageUUID = it
    }
}

fun Page.restoreInOrder(history: List<PageHistory>): Page = apply {
    history.forEach { restoreTo(it) }
}