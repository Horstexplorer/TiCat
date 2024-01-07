package de.hypercdn.ticat.server.data.sql.entities.messages.history

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.config.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import de.hypercdn.ticat.server.data.helper.CopyConstructable
import de.hypercdn.ticat.server.data.sql.entities.messages.Message
import de.hypercdn.ticat.server.data.sql.entities.page.Page
import de.hypercdn.ticat.server.data.sql.entities.page.history.PageHistory
import de.hypercdn.ticat.server.data.sql.entities.user.User
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "message_history")
@DynamicInsert
@DynamicUpdate
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
class MessageHistory : CopyConstructable<PageHistory>  {

    companion object

    @Id
    @Column(
        name = "message_history_uuid",
        nullable = false,
        updatable = false
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    lateinit var uuid: UUID

    @Column(
        name = "message_uuid",
        nullable = false,
        updatable = false
    )
    lateinit var messageUUID: UUID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "message_uuid",
        referencedColumnName = "message_uuid",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    lateinit var message: Message

    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    @ColumnDefault("NOW()")
    @CreationTimestamp
    lateinit var createdAt: OffsetDateTime

    @Column(
        name = "version_id",
        nullable = false,
        updatable = false
    )
    var versionId: Int = -1


    @Column(
        name = "editor_uuid",
        nullable = false,
        updatable = false
    )
    lateinit var editorUUID: UUID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "editor_uuid",
        referencedColumnName = "user_uuid",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    lateinit var editor: User

    @Column(
        name = "old_content",
        updatable = false
    )
    var oldContent: String? = null

    @Column(
        name = "old_setting_status",
        updatable = false
    )
    var oldSettingStatus: Message.Settings.Status? = null

    constructor()

    constructor(other: MessageHistory) {
        if (other::uuid.isInitialized)
            this.uuid = other.uuid
        if (other::messageUUID.isInitialized)
            this.messageUUID = other.messageUUID
        if (other::createdAt.isInitialized)
            this.createdAt = other.createdAt
        this.versionId = other.versionId
        if (other::editorUUID.isInitialized)
            this.editorUUID = other.editorUUID
        this.oldContent = other.oldContent
        this.oldSettingStatus = other.oldSettingStatus
    }

}