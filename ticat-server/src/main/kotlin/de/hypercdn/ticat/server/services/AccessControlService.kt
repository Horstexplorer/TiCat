package de.hypercdn.ticat.server.services

import de.hypercdn.ticat.server.helper.accesscontrol.page.PageAccessController
import de.hypercdn.ticat.server.helper.accesscontrol.user.UserAccessController
import de.hypercdn.ticat.server.helper.accesscontrol.workspace.WorkspaceAccessController
import org.springframework.stereotype.Service

@Service
class AccessControlService {
    val workspace: WorkspaceAccessController = WorkspaceAccessController()
    val page: PageAccessController = PageAccessController()
    val user: UserAccessController = UserAccessController()
}