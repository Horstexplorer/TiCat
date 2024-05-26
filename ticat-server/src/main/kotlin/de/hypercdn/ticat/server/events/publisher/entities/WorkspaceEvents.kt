package de.hypercdn.ticat.server.events.publisher.entities

import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import de.hypercdn.ticat.server.events.publisher.base.*
import de.hypercdn.ticat.server.helper.ModificationContext
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.util.*

interface WorkspacePayload: EntityPayload<Workspace>
open class WorkspaceCreatePayload(
    newEntity: Workspace
) : EntityCreatePayload<Workspace>(newEntity), WorkspacePayload
open class WorkspaceModificationPayload(
    modificationContext: ModificationContext<Workspace>
): EntityModificationPayload<Workspace>(modificationContext), WorkspacePayload
open class WorkspaceDeletePayload(
    deletedEntityId: UUID,
    deletedEntity: Workspace? = null
): EntityDeletePayload<UUID, Workspace>(deletedEntityId, deletedEntity), WorkspacePayload

interface WorkspaceEvent<out T>: EntityEvent<Workspace, T> where T : WorkspacePayload

interface WorkspaceCreateEvent: EntityCreateEvent<Workspace, WorkspaceCreatePayload>, WorkspaceEvent<WorkspaceCreatePayload>
open class WorkspaceCreateEventImp(payload: WorkspaceCreatePayload): GenericEntityCreateEvent<Workspace, WorkspaceCreatePayload>(payload), WorkspaceCreateEvent

interface WorkspaceModificationEvent: EntityModifyEvent<Workspace, WorkspaceModificationPayload>, WorkspaceEvent<WorkspaceModificationPayload>
open class WorkspaceModificationEventImp(payload: WorkspaceModificationPayload): GenericEntityModifyEvent<Workspace, WorkspaceModificationPayload>(payload), WorkspaceModificationEvent

interface WorkspaceDeleteEvent: EntityDeleteEvent<Workspace, WorkspaceDeletePayload>, WorkspaceEvent<WorkspaceDeletePayload>
open class WorkspaceDeleteEventImp(payload: WorkspaceDeletePayload): GenericEntityDeleteEvent<Workspace, WorkspaceDeletePayload>(payload), WorkspaceDeleteEvent

@Service
class WorkspaceEventPublisher(
    applicationEventPublisher: ApplicationEventPublisher
) : EntityEventPublisher<WorkspaceEvent<*>>(applicationEventPublisher) {
    fun publishWorkspaceCreate(workspace: Workspace) = dispatch(WorkspaceCreateEventImp(WorkspaceCreatePayload(workspace)))

    fun publishWorkspaceModification(context: ModificationContext<Workspace>) = dispatch(WorkspaceModificationEventImp(WorkspaceModificationPayload(context)))

    fun publishWorkspaceDelete(id: UUID) = dispatch(WorkspaceDeleteEventImp(WorkspaceDeletePayload(id)))

    fun publishWorkspaceDelete(workspace: Workspace) = dispatch(WorkspaceDeleteEventImp(WorkspaceDeletePayload(workspace.uuid, workspace)))
}