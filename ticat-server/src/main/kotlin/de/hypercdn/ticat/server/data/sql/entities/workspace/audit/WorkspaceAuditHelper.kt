package de.hypercdn.ticat.server.data.sql.entities.workspace.audit

enum class WorkspaceAuditAction {
    CREATED_WORKSPACE,
    MODIFIED_WORKSPACE,
    ARCHIVED_WORKSPACE,
    UNARCHIVED_WORKSPACE,
    DELETED_WORKSPACE,
    REVERTED_FROM_HISTORY
}