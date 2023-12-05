package de.hypercdn.ticat.server.data.sql.entities.workspace.member

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WorkspaceMemberRepository : CrudRepository<WorkspaceMember, WorkspaceMember.Key> {
}