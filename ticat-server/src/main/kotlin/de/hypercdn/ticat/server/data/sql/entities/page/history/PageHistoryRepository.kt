package de.hypercdn.ticat.server.data.sql.entities.page.history

import de.hypercdn.ticat.server.data.sql.entities.page.Page
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PageHistoryRepository : CrudRepository<PageHistory, UUID> {

    @Query("""
        FROM PageHistory pageHistory
        WHERE pageHistory.entity = :page
    """)
    fun getPageHistoriesByPage(
        @Param("page") page: Page
    ): List<PageHistory>

}