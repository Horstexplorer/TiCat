package de.hypercdn.ticat.server.data.sql.entities.workspace.history

import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface WorkspaceHistoryRepository : CrudRepository<WorkspaceHistory, UUID> {

    fun getWorkspaceHistoriesByWorkspace(workspace: Workspace): List<WorkspaceHistory>

}