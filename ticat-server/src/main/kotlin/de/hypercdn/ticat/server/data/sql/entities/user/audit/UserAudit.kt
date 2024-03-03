package de.hypercdn.ticat.server.data.sql.entities.user.audit

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.data.sql.entities.audit.Audit
import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.helper.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.util.*

@Entity
@Table(name = "audit_users")
@DynamicInsert
@DynamicUpdate
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
class UserAudit : Audit<UserAudit, UserAuditAction>() {

    @Column(
        name = "affected_entity_uuid",
        updatable = false
    )
    @ColumnDefault("NULL")
    var affectedEntityUUID: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "affected_entity_uuid",
        referencedColumnName = "user_uuid",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    var affectedEntity: User? = null

    @Column(
        name = "affected_entity_hint",
        updatable = false
    )
    @ColumnDefault("NULL")
    var affectedEntityHint: String? = null

}