package de.hypercdn.ticat.server.data.sql.entities.workspace.member

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.data.helper.CopyConstructable
import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import jakarta.persistence.*
import jakarta.persistence.Table
import lombok.NoArgsConstructor
import org.hibernate.annotations.*
import java.io.Serializable
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@IdClass(WorkspaceMember.Key::class)
@Table(name = "workspace_members")
@DynamicInsert
@DynamicUpdate
@JsonFilter("omitUninitializedLateInitFields")
class WorkspaceMember : CopyConstructable<WorkspaceMember> {

    companion object

    @NoArgsConstructor
    class Key(
        var workspaceUUID: UUID,
        var userUUID: UUID
    ) : Serializable

    @Id
    @Column(
        name = "workspace_uuid",
        nullable = false,
        updatable = false
    )
    lateinit var workspaceUUID: UUID

    @PrimaryKeyJoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "workspace_uuid",
        referencedColumnName = "workspace_uuid",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    lateinit var workspace: Workspace


    @Id
    @Column(
        name = "user_uuid",
        nullable = false,
        updatable = false
    )
    lateinit var userUUID: UUID

    @PrimaryKeyJoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "user_uuid",
        referencedColumnName = "user_uuid",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    lateinit var user: User

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

    @Column(
        name = "membership_status",
        nullable = false
    )
    @ColumnDefault("MEMBERSHIP_DENIED")
    @Enumerated(EnumType.STRING)
    var membershipStatus: MembershipStatus = MembershipStatus.MEMBERSHIP_DENIED

    enum class MembershipStatus {
        MEMBERSHIP_REQUESTED,
        MEMBERSHIP_OFFERED,
        MEMBERSHIP_GRANTED,
        MEMBERSHIP_DENIED
    }

    @Embedded
    var permissions: Permissions = Permissions()

    @Embeddable
    open class Permissions : CopyConstructable<WorkspaceMember> {

        companion object

        @Column(
            name = "permission_pages",
            nullable = false
        )
        @ColumnDefault("CAN_VIEW")
        var pagePermission: PagePermission = PagePermission.CAN_VIEW

        enum class PagePermission(val value: Int) {
            DENY(0),
            CAN_VIEW(1),
            CAN_VIEW_CREATE_EDIT(2),
            CAN_VIEW_CREATE_EDIT_DELETE(3)
        }

        @Column(
            name = "permission_boards",
            nullable = false
        )
        @ColumnDefault("CAN_VIEW")
        var boardPermission: BoardPermission = BoardPermission.CAN_VIEW

        enum class BoardPermission(val value: Int) {
            DENY(0),
            CAN_VIEW(1),
            CAN_VIEW_CREATE_EDIT(2),
            CAN_VIEW_CREATE_EDIT_DELETE(3)
        }

        @Column(
            name = "permission_tickets",
            nullable = false
        )
        @ColumnDefault("CAN_VIEW")
        var ticketPermission: TicketPermission = TicketPermission.CAN_VIEW

        enum class TicketPermission(val value: Int) {
            DENY(0),
            CAN_VIEW(1),
            CAN_VIEW_CREATE_EDIT(2),
            CAN_VIEW_CREATE_EDIT_DELETE(3)
        }

        @Column(
            name = "permission_workspace",
            nullable = false
        )
        @ColumnDefault("CAN_VIEW")
        var workspacePermission: WorkspacePermission = WorkspacePermission.CAN_VIEW

        enum class WorkspacePermission(val value: Int) {
            DENY(0),
            CAN_VIEW(1),
            CAN_VIEW_EDIT(2),
            CAN_VIEW_EDIT_MANAGE_MEMBERS(3),
            CAN_ADMINISTRATE(4),
        }

        constructor()

        constructor(other: Permissions) {
            this.pagePermission = other.pagePermission
            this.boardPermission = other.boardPermission
            this.ticketPermission = other.ticketPermission
            this.workspacePermission = other.workspacePermission
        }

    }

    constructor()

    constructor(other: WorkspaceMember) {
        if (other::userUUID.isInitialized)
            this.userUUID = other.userUUID
        if (other::workspaceUUID.isInitialized)
            this.workspaceUUID = other.workspaceUUID
        if (other::createdAt.isInitialized)
            this.createdAt = other.createdAt
        if (other::modifiedAt.isInitialized)
            this.modifiedAt = other.modifiedAt
        this.membershipStatus = other.membershipStatus
        this.permissions = Permissions(other.permissions)
    }

}