package de.hypercdn.ticat.server.data.sql.entities.messages

import de.hypercdn.ticat.server.data.sql.entities.page.Page
import de.hypercdn.ticat.server.data.sql.entities.ticket.Ticket
import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface MessageRepository : CrudRepository<Message, UUID> {

    @Query("""
        SELECT DISTINCT message.recipient.userUUID
        FROM Message message
        WHERE message.senderUUID = :#{sender.uuid}
            AND message.recipient.userUUID IS NOT NULL
    """)
    fun getPrivateMessagesReceiversBySender(sender: User): List<UUID>

    @Query("""
        FROM Message message
        WHERE message.recipient.workspaceUUID = :#{recipient.uuid}
    """)
    fun getMessagesByContext(recipient: Workspace): List<Message>

    @Query("""
        FROM Message message
        WHERE message.recipient.ticketUUID= :#{recipient.uuid}
    """)
    fun getMessagesByContext(recipient: Ticket): List<Message>

    @Query("""
        FROM Message message
        WHERE message.recipient.pageUUID = :#{recipient.uuid}
    """)
    fun getMessagesByContext(recipient: Page): List<Message>

    @Query("""
        FROM Message message
        WHERE message.recipient.userUUID = :#{recipient.uuid}
            AND message.senderUUID = :#{sender.uuid}
    """)
    fun getMessageByContext(sender: User, recipient: User): List<Message>

}