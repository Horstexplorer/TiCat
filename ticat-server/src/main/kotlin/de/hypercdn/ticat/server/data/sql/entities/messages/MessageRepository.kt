package de.hypercdn.ticat.server.data.sql.entities.messages

import de.hypercdn.ticat.server.data.sql.entities.page.Page
import de.hypercdn.ticat.server.data.sql.entities.ticket.Ticket
import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface MessageRepository : CrudRepository<Message, UUID> {

//    @Query("""
//        SELECT DISTINCT message.recipient.userUUID
//        FROM Message message
//        WHERE message.sender = :sender
//            AND message.recipient.userUUID IS NOT NULL
//    """)
//    fun getPrivateMessagesReceivers(sender: User): List<UUID>

//    @Query("""
//        FROM Message message
//        WHERE message.recipient.workspace = :recipient
//    """)
//    fun getMessagesByContext(recipient: Workspace): List<Message>
//
//    @Query("""
//        FROM Message message
//        WHERE message.recipient.ticket= :recipient
//    """)
//    fun getMessagesByContext(recipient: Ticket): List<Message>
//
//    @Query("""
//        FROM Message message
//        WHERE message.recipient.page = :recipient
//    """)
//    fun getMessagesByContext(recipient: Page): List<Message>
//
//    @Query("""
//        FROM Message message
//        WHERE message.recipient.user = :recipient
//            AND message.sender = :sender
//    """)
//    fun getMessageByContext(sender: User, recipient: User): List<Message>

}