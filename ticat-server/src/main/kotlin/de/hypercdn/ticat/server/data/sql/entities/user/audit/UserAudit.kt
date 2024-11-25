package de.hypercdn.ticat.server.data.sql.entities.user.audit

import com.fasterxml.jackson.annotation.JsonFilter
import de.hypercdn.ticat.server.data.sql.base.audit.Audit
import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.helper.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate

@Entity
@Table(name = "audit_users")
@DynamicInsert
@DynamicUpdate
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
class UserAudit : Audit<User, UserAudit, UserAudit.AuditAction> {

    enum class AuditAction {
        UPDATED_USER_DETAILS,
        MODIFIED_SETTINGS,
        MODIFIED_PERMISSIONS
    }

    constructor(): super()

    constructor(other: UserAudit): super(other)

}