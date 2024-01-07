package de.hypercdn.ticat.server.data.sql.entities.user

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : CrudRepository<User, UUID> {

    fun getUserByAuthSubjectReference(reference: String): User?

}