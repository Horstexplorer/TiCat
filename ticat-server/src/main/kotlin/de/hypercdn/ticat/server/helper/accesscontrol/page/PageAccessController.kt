package de.hypercdn.ticat.server.helper.accesscontrol.page

import de.hypercdn.ticat.server.data.sql.entities.page.Page
import de.hypercdn.ticat.server.helper.accesscontrol.AccessRequest
import de.hypercdn.ticat.server.helper.accesscontrol.EntityAccessController
import de.hypercdn.ticat.server.helper.accesscontrol.page.rules.*
import de.hypercdn.ticat.server.helper.accesscontrol.workspace.WorkspaceScopedAccessor

enum class PageExecutableAction : AccessRequest {
    CREATE_PAGE,            // create new page
    VIEW_PAGE,              // view page details
    MODIFY_PAGE,            // modify page
    MODIFY_SETTINGS,        // modify page settings
    DELETE_PAGE,            // delete page
    REVERT_FROM_HISTORY     // revert page to state defined in history
}

class PageAccessController : EntityAccessController<Page, PageExecutableAction, WorkspaceScopedAccessor>() {
    override val accessRules = listOf(
        CreatePageRule(),
        ViewPageRule(),
        ModifyPageRule(),
        ModifyPageSettingsRule(),
        DeletePageRule(),
        RevertPageFromHistoryRule()
    )
}