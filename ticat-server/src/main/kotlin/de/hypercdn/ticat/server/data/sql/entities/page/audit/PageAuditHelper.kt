package de.hypercdn.ticat.server.data.sql.entities.page.audit

enum class PageAuditAction {
    CREATED_PAGE,
    MODIFIED_PAGE,
    ARCHIVED_PAGE,
    UNARCHIVED_PAGE,
    DELETED_PAGE,
    REVERTED_FROM_HISTORY
}