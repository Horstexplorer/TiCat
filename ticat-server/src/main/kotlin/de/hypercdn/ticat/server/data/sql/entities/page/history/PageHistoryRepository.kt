package de.hypercdn.ticat.server.data.sql.entities.page.history

import de.hypercdn.ticat.server.data.sql.entities.page.Page
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PageHistoryRepository : CrudRepository<PageHistory, UUID> {

    fun getPageHistoriesByPage(page: Page): List<PageHistory>

}