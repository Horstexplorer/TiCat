package de.hypercdn.ticat.server.data.sql.entities.workspace.member.audit

import de.hypercdn.ticat.server.data.sql.entities.ticket.audit.TicketAudit
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import de.hypercdn.ticat.server.data.sql.entities.workspace.audit.WorkspaceAudit
import de.hypercdn.ticat.server.data.sql.entities.workspace.member.WorkspaceMember
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface WorkspaceMemberAuditRepository : CrudRepository<WorkspaceAudit, UUID> {

    @Query("""
        FROM WorkspaceMemberAudit workspaceMemberAudit
        WHERE workspaceMemberAudit.parentEntity = :workspace
    """)
    fun getWorkspaceMemberAuditsByWorkspace(workspace: Workspace): List<TicketAudit>

    @Query("""
        FROM WorkspaceMemberAudit workspaceMemberAudit
        WHERE workspaceMemberAudit.affectedEntity = :workspaceMember
    """)
    fun getWorkspaceMemberAuditsByMember(workspaceMember: WorkspaceMember): List<WorkspaceMemberAudit>

}