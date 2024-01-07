package de.hypercdn.ticat.server.data.sql.entities.page

import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PageRepository : CrudRepository<Page, UUID> {

    fun getPagesByWorkspace(workspace: Workspace): List<Page>

    fun getPagesBySettingsParentPage(parentPage: Page): List<Page>

}