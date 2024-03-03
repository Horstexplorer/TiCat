package de.hypercdn.ticat.server.data.sql.entities.workspace.member.audit

enum class WorkspaceMemberAuditAction {
    MEMBERSHIP_REQUESTED,
    MEMBERSHIP_OFFERED,
    MEMBERSHIP_GRANTED,
    MEMBERSHIP_DENIED,
    MODIFIED_PERMISSIONS
}