package de.hypercdn.ticat.server.data.sql.entities.message.history

import de.hypercdn.ticat.server.data.sql.entities.message.Message
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface MessageHistoryRepository : CrudRepository<MessageHistory, UUID> {

    @Query("""
        FROM MessageHistory messageHistory
        WHERE messageHistory.entity = :message
    """)
    fun getMessageHistoriesByMessage(
        @Param("message") message: Message
    ): List<MessageHistory>

}