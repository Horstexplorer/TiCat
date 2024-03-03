package de.hypercdn.ticat.server.data.sql.entities.message.audit

enum class MessageAuditAction {
    CREATED_MESSAGE,
    MODIFIED_MESSAGE_CONTENT,
    ARCHIVED_MESSAGE,
    DELETED_MESSAGE,
    REVERTED_FROM_HISTORY
}