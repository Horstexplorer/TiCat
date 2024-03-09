package de.hypercdn.ticat.server.data.sql.entities.workspace.history

import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface WorkspaceHistoryRepository : CrudRepository<WorkspaceHistory, UUID> {

    @Query("""
        FROM WorkspaceHistory workspaceHistory
        WHERE workspaceHistory.entity = :workspace
    """)
    fun getWorkspaceHistoriesByEntity(
        @Param("workspace") workspace: Workspace
    ): List<WorkspaceHistory>

}