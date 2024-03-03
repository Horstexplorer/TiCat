package de.hypercdn.ticat.server.data.sql.entities.workspace.member.audit

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.data.sql.entities.audit.Audit
import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import de.hypercdn.ticat.server.data.sql.entities.workspace.member.WorkspaceMember
import de.hypercdn.ticat.server.helper.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.util.*

@Entity
@Table(name = "audit_workspace_members")
@DynamicInsert
@DynamicUpdate
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
class WorkspaceMemberAudit : Audit<WorkspaceMemberAudit, WorkspaceMemberAuditAction>() {

    @Column(
        name = "affected_entity_workspace_uuid",
        updatable = false
    )
    @ColumnDefault("NULL")
    var affectedEntityWorkspaceUUID: UUID? = null

    @Column(
        name = "affected_entity_user_uuid",
        updatable = false
    )
    @ColumnDefault("NULL")
    var affectedEntityUserUUID: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns(
        JoinColumn(
            name = "affected_entity_workspace_uuid",
            referencedColumnName = "workspace_uuid",
            insertable = false,
            updatable = false
        ),
        JoinColumn(
            name = "affected_entity_user_uuid",
            referencedColumnName = "user_uuid",
            insertable = false,
            updatable = false
        )
    )
    @JsonIgnore
    var affectedEntity: WorkspaceMember? = null

    @Column(
        name = "affected_entity_workspace_hint",
        updatable = false
    )
    @ColumnDefault("NULL")
    var affectedEntityWorkspaceHint: String? = null

    @Column(
        name = "affected_entity_user_hint",
        updatable = false
    )
    @ColumnDefault("NULL")
    var affectedEntityUserHint: String? = null

}