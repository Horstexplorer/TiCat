package de.hypercdn.ticat.server.data.sql.entities.user

import com.fasterxml.jackson.annotation.JsonFilter
import de.hypercdn.ticat.server.helper.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import de.hypercdn.ticat.server.data.helper.CopyConstructable
import jakarta.persistence.*
import jakarta.persistence.Table
import org.hibernate.annotations.*
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "users")
@DynamicInsert
@DynamicUpdate
@Cacheable(true)
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
class User : CopyConstructable<User> {

    companion object

    @Id
    @Column(
        name = "user_uuid",
        nullable = false,
        updatable = false
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    lateinit var uuid: UUID

    @Column(
        name = "auth_subject_reference",
        nullable = true,
        updatable = false
    )
    @ColumnDefault("NULL")
    var authSubjectReference: String? = null

    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    @ColumnDefault("NOW()")
    @CreationTimestamp
    lateinit var createdAt: OffsetDateTime

    @Column(
        name = "modified_at",
        nullable = false
    )
    @ColumnDefault("NOW()")
    @UpdateTimestamp
    lateinit var modifiedAt: OffsetDateTime

    @Embedded
    var name: Name = Name()

    @Embeddable
    class Name : CopyConstructable<Name> {

        companion object

        @Column(
            name = "first_name"
        )
        @ColumnDefault("NULL")
        var firstName: String? = null

        @Column(
            name = "last_name"
        )
        @ColumnDefault("NULL")
        var lastName: String? = null

        @Column(
            name = "display_name",
            nullable = false
        )
        lateinit var displayName: String

        constructor()

        constructor(other: Name) {
            this.firstName = other.firstName
            this.lastName = other.lastName
            if (other::displayName.isInitialized)
                this.displayName = other.displayName
        }

    }

    @Column(
        name = "email"
    )
    @ColumnDefault("NULL")
    var email: String? = null

    @Column(
        name = "account_type"
    )
    @ColumnDefault("DEFAULT")
    @Enumerated(EnumType.STRING)
    var accountType: AccountType = AccountType.DEFAULT

    enum class AccountType {
        SYSTEM,
        ADMIN,
        DEFAULT
    }

    @Embedded
    var permissions: Permissions = Permissions()

    @Embeddable
    open class Permissions : CopyConstructable<Permissions> {

        companion object

        @Column(
            name = "permission_can_create_new_workspaces",
            nullable = false
        )
        @ColumnDefault("false")
        var canCreateNewWorkspaces: Boolean = false

        @Column(
            name = "permission_can_request_workspace_access",
            nullable = false
        )
        @ColumnDefault("false")
        var canRequestWorkspaceAccess: Boolean = false

        @Column(
            name = "permission_can_send_messages_to_users",
            nullable = false
        )
        @ColumnDefault("false")
        var canSendMessagesToUsers: Boolean = false

        constructor()

        constructor(other: Permissions) {
            this.canCreateNewWorkspaces = other.canCreateNewWorkspaces
            this.canRequestWorkspaceAccess = other.canRequestWorkspaceAccess
            this.canSendMessagesToUsers = other.canSendMessagesToUsers
        }

    }

    @Embedded
    var settings: Settings = Settings()

    @Embeddable
    class Settings : CopyConstructable<Settings> {

        @Column(
            name = "setting_receive_workspace_invitations_from_origin",
            nullable = false
        )
        @ColumnDefault("ANYONE")
        @Enumerated(EnumType.STRING)
        var receiveWorkspaceInvitationsFromOrigin: WorkspaceInvitationOrigin = WorkspaceInvitationOrigin.ANYONE

        enum class WorkspaceInvitationOrigin {
            ANYONE,
            NOBODY
        }

        @Column(
            name = "setting_receive_messages_from_origin",
            nullable = false
        )
        @ColumnDefault("WORKSPACE_MEMBER")
        @Enumerated(EnumType.STRING)
        var receiveMessagesFromOrigin: MessageOrigin = MessageOrigin.WORKSPACE_MEMBER

        enum class MessageOrigin {
            ANYONE,
            WORKSPACE_MEMBER,
            NOBODY
        }

        @Column(
            name = "setting_status",
            nullable = false
        )
        @ColumnDefault("ACTIVE")
        @Enumerated(EnumType.STRING)
        var status: Status = Status.ACTIVE

        enum class Status {
            ACTIVE,
            DISABLED
        }

        @Column(
            name = "setting_locale"
        )
        @ColumnDefault("NULL")
        var locale: Locale? = null

        constructor()

        constructor(other: Settings) {
            this.receiveWorkspaceInvitationsFromOrigin = other.receiveWorkspaceInvitationsFromOrigin
            this.receiveMessagesFromOrigin = other.receiveMessagesFromOrigin
            this.status = other.status
            this.locale = other.locale
        }

    }

    constructor()

    constructor(other: User) {
        if (other::uuid.isInitialized)
            this.uuid = other.uuid
        this.authSubjectReference = other.authSubjectReference
        if (other::createdAt.isInitialized)
            this.createdAt = other.createdAt
        if (other::modifiedAt.isInitialized)
            this.modifiedAt = other.modifiedAt
        this.name = Name(other.name)
        this.email = other.email
        this.accountType = other.accountType
        this.permissions = Permissions(other.permissions)
        this.settings = Settings(other.settings)
    }

}