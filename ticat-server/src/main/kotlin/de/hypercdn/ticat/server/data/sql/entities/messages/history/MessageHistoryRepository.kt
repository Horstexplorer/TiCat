package de.hypercdn.ticat.server.data.sql.entities.messages.history

import de.hypercdn.ticat.server.data.sql.entities.messages.Message
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface MessageHistoryRepository : CrudRepository<MessageHistory, UUID> {

    fun getMessageHistoriesByMessage(message: Message): List<MessageHistory>

}