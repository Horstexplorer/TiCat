package de.hypercdn.ticat.server.data.sql.entities.board.audit

enum class BoardAuditAction {
    CREATED_BOARD,
    MODIFIED_BOARD,
    ARCHIVED_BOARD,
    UNARCHIVED_BOARD,
    DELETED_BOARD
}