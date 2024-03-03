package de.hypercdn.ticat.server.data.sql.entities.board.audit

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.data.sql.entities.audit.Audit
import de.hypercdn.ticat.server.data.sql.entities.board.Board
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import de.hypercdn.ticat.server.helper.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
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
class BoardAudit : Audit<BoardAudit, BoardAuditAction>() {

    @Column(
        name = "affected_entity_uuid",
        updatable = false
    )
    @ColumnDefault("NULL")
    var affectedEntityUUID: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "affected_entity_uuid",
        referencedColumnName = "board_uuid",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    var affectedEntity: Board? = null

    @Column(
        name = "affected_entity_hint",
        updatable = false
    )
    @ColumnDefault("NULL")
    var affectedEntityHint: String? = null

    @Column(
        name = "parent_entity_uuid",
        updatable = false
    )
    @ColumnDefault("NULL")
    var parentEntityUUID: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "parent_entity_uuid",
        referencedColumnName = "workspace_uuid",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    var parentEntity: Workspace? = null

    @Column(
        name = "parent_entity_hint",
        updatable = false
    )
    @ColumnDefault("NULL")
    var parentEntityHint: String? = null

}