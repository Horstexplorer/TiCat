package de.hypercdn.ticat.server.data.sql.entities.workspace.audit

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.data.sql.entities.audit.Audit
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import de.hypercdn.ticat.server.data.sql.entities.workspace.history.WorkspaceHistory
import de.hypercdn.ticat.server.helper.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.util.*

@Entity
@Table(name = "audit_workspaces")
@DynamicInsert
@DynamicUpdate
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
class WorkspaceAudit : Audit<WorkspaceAudit, WorkspaceAuditAction>() {

    @Column(
        name = "affected_entity_uuid",
        updatable = false
    )
    @ColumnDefault("NULL")
    var affectedEntityUUID: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "affected_entity_uuid",
        referencedColumnName = "workspace_uuid",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    var affectedEntity: Workspace? = null

    @Column(
        name = "affected_entity_hint",
        updatable = false
    )
    @ColumnDefault("NULL")
    var affectedEntityHint: String? = null


    @Column(
        name = "change_history_uuid",
        updatable = false
    )
    @ColumnDefault("NULL")
    var changeHistoryUUID: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "change_history_uuid",
        referencedColumnName = "history_uuid",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    var changeHistory: WorkspaceHistory? = null

}