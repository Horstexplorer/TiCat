package de.hypercdn.ticat.server.services

import de.hypercdn.ticat.server.data.sql.entities.board.audit.BoardAuditRepository
import de.hypercdn.ticat.server.data.sql.entities.message.audit.MessageAuditRepository
import de.hypercdn.ticat.server.data.sql.entities.page.audit.PageAuditRepository
import de.hypercdn.ticat.server.data.sql.entities.ticket.audit.TicketAuditRepository
import de.hypercdn.ticat.server.data.sql.entities.user.audit.UserAuditRepository
import de.hypercdn.ticat.server.data.sql.entities.workspace.audit.WorkspaceAuditRepository
import de.hypercdn.ticat.server.data.sql.entities.workspace.member.audit.WorkspaceMemberAuditRepository
import org.springframework.stereotype.Service

/**
 * Service to create and persist audit records
 */
@Service
class AuditService (
    val boardAuditRepository: BoardAuditRepository,
    val messageAuditRepository: MessageAuditRepository,
    val pageAuditRepository: PageAuditRepository,
    val ticketAuditRepository: TicketAuditRepository,
    val userAuditRepository: UserAuditRepository,
    val workspaceAuditRepository: WorkspaceAuditRepository,
    val workspaceMemberAuditRepository: WorkspaceMemberAuditRepository
) {

}