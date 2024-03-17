package de.hypercdn.ticat.server.data.sql.entities.user.audit

import de.hypercdn.ticat.server.data.sql.entities.user.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserAuditRepository : CrudRepository<UserAudit, UUID> {

    @Query("""
        FROM UserAudit userAudit
        WHERE userAudit.affectedEntity = :user
    """)
    fun getUserAuditsByUser(user: User): List<UserAudit>

}