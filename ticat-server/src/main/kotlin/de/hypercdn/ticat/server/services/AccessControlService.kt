package de.hypercdn.ticat.server.services

import de.hypercdn.ticat.server.helper.accesscontrol.workspace.WorkspaceAccessController
import org.springframework.stereotype.Service

@Service
class AccessControlService {
    val workspace: WorkspaceAccessController = WorkspaceAccessController()
}