package de.hypercdn.ticat.server.data.sql.entities.workspace.audit

import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface WorkspaceAuditRepository: CrudRepository<WorkspaceAudit, UUID> {

    @Query("""
        FROM WorkspaceAudit workspaceAudit
        WHERE workspaceAudit.affectedEntity = :workspace
    """)
    fun getWorkspaceAuditsByWorkspace(workspace: Workspace): List<WorkspaceAudit>

}