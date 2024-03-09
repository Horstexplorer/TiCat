package de.hypercdn.ticat.server.data.sql.entities.board.audit

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.data.sql.base.audit.Audit
import de.hypercdn.ticat.server.data.sql.base.audit.ParentedAudit
import de.hypercdn.ticat.server.data.sql.entities.board.Board
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import de.hypercdn.ticat.server.helper.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import de.hypercdn.ticat.server.helper.constructor.CopyConstructable
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.util.*

@Entity
@Table(name = "audit_boards")
@DynamicInsert
@DynamicUpdate
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
class BoardAudit : ParentedAudit<Board, BoardAudit, BoardAudit.AuditAction> {

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