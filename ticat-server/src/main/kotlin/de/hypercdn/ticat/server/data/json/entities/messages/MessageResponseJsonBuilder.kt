package de.hypercdn.ticat.server.data.json.entities.messages

import de.hypercdn.ticat.server.helper.EntityTemplateBuilder
import de.hypercdn.ticat.server.data.json.entities.page.PageResponseJson
import de.hypercdn.ticat.server.data.json.entities.page.PageResponseJsonBuilder
import de.hypercdn.ticat.server.data.json.entities.page.builder
import de.hypercdn.ticat.server.data.json.entities.ticket.TicketResponseJson
import de.hypercdn.ticat.server.data.json.entities.ticket.TicketResponseJsonBuilder
import de.hypercdn.ticat.server.data.json.entities.ticket.builder
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJson
import de.hypercdn.ticat.server.data.json.entities.user.UserResponseJsonBuilder
import de.hypercdn.ticat.server.data.json.entities.user.builder
import de.hypercdn.ticat.server.data.json.entities.workspace.WorkspaceResponseJson
import de.hypercdn.ticat.server.data.json.entities.workspace.WorkspaceResponseJsonBuilder
import de.hypercdn.ticat.server.data.json.entities.workspace.builder
import de.hypercdn.ticat.server.data.sql.entities.message.Message
import de.hypercdn.ticat.server.data.sql.entities.page.Page
import de.hypercdn.ticat.server.data.sql.entities.ticket.Ticket
import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace

class MessageResponseJsonBuilder(
    private val message: Message? = null
): EntityTemplateBuilder<MessageResponseJsonBuilder, MessageResponseJson>({ MessageResponseJson() }) {

    fun includeUUID(skip: Boolean = false): MessageResponseJsonBuilder = modify(skip) {
        it.uuid = message?.uuid
    }

    fun includeVersionTimestamp(skip: Boolean = false): MessageResponseJsonBuilder = modify(skip) {
        it.versionTimestamp = message?.modifiedAt
    }

    fun includeSender(skip: Boolean = false, user: User? = message?.sender, configurator: (UserResponseJsonBuilder) -> Unit): MessageResponseJsonBuilder = modify(skip) {
        it.sender = UserResponseJson.builder(user)
            .apply(configurator)
            .build()
    }

    class MessageRecipientJsonBuilder(
        private val recipient: Message.Recipient? = null
    ): EntityTemplateBuilder<MessageRecipientJsonBuilder, MessageResponseJson.Recipient>({ MessageResponseJson.Recipient() }) {

        fun includeWorkspace(skip: Boolean = false, workspace: Workspace? = recipient?.workspace, configurator: (WorkspaceResponseJsonBuilder) -> Unit): MessageRecipientJsonBuilder = modify(skip) {
            it.workspace = WorkspaceResponseJson.builder(workspace)
                .apply(configurator)
                .build()
        }

        fun includePage(skip: Boolean = false, page: Page? = recipient?.page, configurator: (PageResponseJsonBuilder) -> Unit): MessageRecipientJsonBuilder = modify(skip) {
            it.page = PageResponseJson.builder(page)
                .apply(configurator)
                .build()
        }

        fun includeTicket(skip: Boolean = false, ticket: Ticket? = recipient?.ticket, configurator: (TicketResponseJsonBuilder) -> Unit): MessageRecipientJsonBuilder = modify(skip) {
            it.ticket = TicketResponseJson.builder(ticket)
                .apply(configurator)
                .build()
        }

        fun includeUser(skip: Boolean = false, user: User? = recipient?.user, configurator: (UserResponseJsonBuilder) -> Unit): MessageRecipientJsonBuilder = modify(skip) {
            it.user = UserResponseJson.builder(user)
                .apply(configurator)
                .build()
        }

    }

    fun includeRecipient(skip: Boolean = false, recipient: Message.Recipient? = message?.recipient, recipientConfigurator: (MessageRecipientJsonBuilder) -> Unit): MessageResponseJsonBuilder = modify(skip) {
        it.recipient = MessageRecipientJsonBuilder(recipient)
            .apply(recipientConfigurator)
            .build()
    }

    fun includeParentMessage(skip: Boolean = false, parentMessage: Message? = message?.parentMessage, configurator: (MessageResponseJsonBuilder) -> Unit): MessageResponseJsonBuilder = modify(skip) {
        it.parentMessage = MessageResponseJson.builder(parentMessage)
            .apply(configurator)
            .build()
    }

    fun includeSettings(skip: Boolean = false): MessageResponseJsonBuilder = modify(skip) {
        it.settings = MessageResponseJson.Settings().apply {
            status = message?.settings?.status
        }
    }

    fun includeContent(skip: Boolean = false): MessageResponseJsonBuilder = modify(skip) {
        it.content = message?.content
    }

}

fun MessageResponseJson.Companion.builder(message: Message? = null): MessageResponseJsonBuilder = MessageResponseJsonBuilder(message)
fun Message.toJsonResponseBuilder(): MessageResponseJsonBuilder = MessageResponseJsonBuilder(this)