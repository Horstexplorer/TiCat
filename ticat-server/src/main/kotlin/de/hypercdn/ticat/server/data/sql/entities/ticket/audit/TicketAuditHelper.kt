package de.hypercdn.ticat.server.data.sql.entities.ticket.audit

enum class TicketAuditAction {
    CREATED_TICKET,
    MODIFIED_TICKET_CONTENT,
    MODIFIED_TICKET_SETTINGS,
    ARCHIVED_TICKET,
    UNARCHIVED_TICKET,
    DELETED_TICKET,
    REVERTED_FROM_HISTORY
}