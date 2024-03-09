package de.hypercdn.ticat.server.data.sql.entities.user.authcache

import jakarta.persistence.Cacheable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@Cacheable(true)
interface UserAuthCacheRepository : CrudRepository<UserAuthCacheReference, UUID> {

    fun existsByAuthSubjectReferenceAndAuthIdentifier(authSubjectReference: String, authIdentifier: String): Boolean

    @Modifying
    @Query("""
        DELETE FROM UserAuthCacheReference userAuthCacheRef 
        WHERE CURRENT_TIMESTAMP > userAuthCacheRef.predictedExpiry
    """)
    fun deleteAllExpired()

}