package de.hypercdn.ticat.server.data.sql.entities.page.audit

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.data.sql.base.audit.Audit
import de.hypercdn.ticat.server.data.sql.base.audit.ParentedAudit
import de.hypercdn.ticat.server.data.sql.entities.page.Page
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import de.hypercdn.ticat.server.data.sql.entities.workspace.history.WorkspaceHistory
import de.hypercdn.ticat.server.helper.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.util.*

@Entity
@Table(name = "audit_pages")
@DynamicInsert
@DynamicUpdate
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
class PageAudit : ParentedAudit<Page, PageAudit, PageAudit.AuditAction> {

    enum class AuditAction {
        CREATED_PAGE,
        MODIFIED_PAGE,
        ARCHIVED_PAGE,
        UNARCHIVED_PAGE,
        DELETED_PAGE,
        REVERTED_FROM_HISTORY
    }

    @Column(
        name = "change_history_uuid",
        updatable = false
    )
    @ColumnDefault("NULL")
    var changeHistoryUUID: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "change_history_uuid",
        referencedColumnName = "uuid",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    var changeHistory: WorkspaceHistory? = null

    constructor(): super()

    constructor(other: PageAudit): super(other) {
        this.changeHistoryUUID = other.changeHistoryUUID
    }

}