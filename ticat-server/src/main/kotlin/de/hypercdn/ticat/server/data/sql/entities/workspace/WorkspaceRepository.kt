package de.hypercdn.ticat.server.data.sql.entities.workspace

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface WorkspaceRepository : CrudRepository<Workspace, UUID> {

    fun getWorkspaceByHid(hid: String)

}