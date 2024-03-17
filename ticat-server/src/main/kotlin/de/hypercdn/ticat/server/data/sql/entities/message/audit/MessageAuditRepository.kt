package de.hypercdn.ticat.server.data.sql.entities.message.audit

import de.hypercdn.ticat.server.data.sql.entities.message.Message
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MessageAuditRepository : CrudRepository<MessageAudit, UUID> {

    @Query("""
        FROM MessageAudit messageAudit
        WHERE messageAudit.affectedEntity = :message
    """)
    fun getMessageAuditsByMessage(message: Message): List<MessageAudit>

}