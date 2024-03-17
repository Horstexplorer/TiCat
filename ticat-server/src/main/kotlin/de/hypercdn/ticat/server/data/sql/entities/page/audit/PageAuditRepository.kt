package de.hypercdn.ticat.server.data.sql.entities.page.audit

import de.hypercdn.ticat.server.data.sql.entities.page.Page
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PageAuditRepository : CrudRepository<PageAudit, UUID> {

    @Query("""
        FROM PageAudit pageAudit
        WHERE pageAudit.parentEntity = :workspace
    """)
    fun getPageAuditsByWorkspace(workspace: Workspace): List<PageAudit>

    @Query("""
        FROM PageAudit pageAudit
        WHERE pageAudit.affectedEntity = :page
    """)
    fun getPageAuditsByPage(page: Page): List<PageAudit>

}