package de.hypercdn.ticat.server.data.sql.entities.board.audit

import com.fasterxml.jackson.annotation.JsonFilter
import de.hypercdn.ticat.server.data.sql.base.audit.ParentedAudit
import de.hypercdn.ticat.server.data.sql.entities.board.Board
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import de.hypercdn.ticat.server.helper.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate

@Entity
@Table(name = "audit_boards")
@DynamicInsert
@DynamicUpdate
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
class BoardAudit : ParentedAudit<Workspace, Board, BoardAudit, BoardAudit.AuditAction> {

    enum class AuditAction {
        CREATED_BOARD,
        MODIFIED_BOARD,
        ARCHIVED_BOARD,
        UNARCHIVED_BOARD,
        DELETED_BOARD
    }

    constructor(): super()

    constructor(other: BoardAudit): super(other)

}