package de.hypercdn.ticat.server.data.sql.entities.workspace.member.audit

import com.fasterxml.jackson.annotation.JsonFilter
import de.hypercdn.ticat.server.data.sql.base.audit.ParentedAudit
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import de.hypercdn.ticat.server.data.sql.entities.workspace.member.WorkspaceMember
import de.hypercdn.ticat.server.helper.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate

@Entity
@Table(name = "audit_workspace_members")
@DynamicInsert
@DynamicUpdate
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
class WorkspaceMemberAudit : ParentedAudit<Workspace, WorkspaceMember, WorkspaceMemberAudit, WorkspaceMemberAudit.AuditAction> {

    enum class AuditAction {
        MEMBERSHIP_REQUESTED,
        MEMBERSHIP_OFFERED,
        MEMBERSHIP_GRANTED,
        MEMBERSHIP_DENIED,
        MODIFIED_PERMISSIONS
    }

    constructor(): super()

    constructor(other: WorkspaceMemberAudit): super(other)

}