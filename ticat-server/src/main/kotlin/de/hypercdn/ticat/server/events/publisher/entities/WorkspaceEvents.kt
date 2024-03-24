package de.hypercdn.ticat.server.events.publisher.entities

import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import de.hypercdn.ticat.server.events.publisher.base.*
import java.util.UUID

typealias WorkspacePayload = EntityPayload<Workspace>
typealias WorkspaceCreatePayload = EntityCreatePayload<Workspace>
typealias WorkspaceModificationPayload = EntityModificationPayload<Workspace>
typealias WorkspaceDeletePayload = EntityDeletePayload<UUID, Workspace>

interface WorkspaceEvent<T>: EntityEvent<T> where T : WorkspacePayload
open class GenericWorkspaceEvent<T>(payload: T): GenericEntityEvent<T>(payload), WorkspaceEvent<T> where T : WorkspacePayload

interface WorkspaceCreateEvent: EntityCreateEvent<Workspace>, WorkspaceEvent<WorkspaceCreatePayload>
class WorkspaceCreateEventImp(payload: WorkspaceCreatePayload): GenericWorkspaceEvent<WorkspaceCreatePayload>(payload), WorkspaceCreateEvent

interface WorkspaceModificationEvent: EntityModificationEvent<Workspace>, WorkspaceEvent<WorkspaceModificationPayload>
class WorkspaceModificationEventImp(payload: WorkspaceModificationPayload): GenericWorkspaceEvent<WorkspaceModificationPayload>(payload), WorkspaceModificationEvent

interface WorkspaceDeleteEvent: EntityDeleteEvent<UUID, Workspace>, WorkspaceEvent<WorkspaceDeletePayload>
class WorkspaceDeleteEventImp(payload: WorkspaceDeletePayload): GenericWorkspaceEvent<WorkspaceDeletePayload>(payload), WorkspaceDeleteEvent
