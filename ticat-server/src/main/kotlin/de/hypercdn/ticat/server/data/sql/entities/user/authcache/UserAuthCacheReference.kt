package de.hypercdn.ticat.server.data.sql.entities.user.authcache

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import de.hypercdn.ticat.server.helper.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import de.hypercdn.ticat.server.data.helper.CopyConstructable
import de.hypercdn.ticat.server.data.sql.entities.user.User
import jakarta.persistence.*
import lombok.NoArgsConstructor
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable
import java.time.OffsetDateTime

@Entity
@IdClass(UserAuthCacheReference.Key::class)
@Table(name = "user_auth_cache")
@DynamicInsert
@DynamicUpdate
@Cacheable(true)
@JsonFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER)
class UserAuthCacheReference : CopyConstructable<User>  {

    companion object

    @NoArgsConstructor
    class Key(
        var authSubjectReference: String,
        var authIdentifier: String
    ) : Serializable

    @Id
    @Column(
        name = "auth_subject_reference",
        nullable = false,
        updatable = false
    )
    lateinit var authSubjectReference: String

    @PrimaryKeyJoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "auth_subject_reference",
        referencedColumnName = "auth_subject_reference",
        insertable = false,
        updatable = false
    )
    @JsonIgnore
    lateinit var user: User

    @Id
    @Column(
        name = "auth_identifier",
        nullable = false,
        updatable = false
    )
    lateinit var authIdentifier: String

    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    @ColumnDefault("NOW()")
    @CreationTimestamp
    lateinit var createdAt: OffsetDateTime

    @Column(
        name = "predicted_expiry",
        nullable = false,
        updatable = false
    )
    lateinit var predictedExpiry: OffsetDateTime

    constructor()

    constructor(other: UserAuthCacheReference) {
        if (other::authSubjectReference.isInitialized)
            this.authSubjectReference = other.authSubjectReference
        if (other::authIdentifier.isInitialized)
            this.authIdentifier = other.authIdentifier
        if (other::createdAt.isInitialized)
            this.createdAt = other.createdAt
        if (other::predictedExpiry.isInitialized)
            this.predictedExpiry = other.predictedExpiry
    }

}