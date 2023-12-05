package de.hypercdn.ticat.server.data.sql.entities.user.authcache

import jakarta.persistence.Cacheable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
@Cacheable(true)
interface UserAuthCacheRepository : CrudRepository<UserAuthCacheReference, UserAuthCacheReference.Key> {

    @Query("""
        DELETE FROM UserAuthCacheReference uacr WHERE uacr.predictedExpiry < NOW()
    """)
    fun deleteAllExpired()

}