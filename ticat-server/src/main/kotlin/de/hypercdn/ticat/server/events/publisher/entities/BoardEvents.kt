package de.hypercdn.ticat.server.events.publisher.entities

import de.hypercdn.ticat.server.data.sql.entities.board.Board
import de.hypercdn.ticat.server.events.publisher.base.*
import de.hypercdn.ticat.server.helper.ModificationContext
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.util.*

interface BoardPayload: EntityPayload<Board>
open class BoardCreatePayload(
    newEntity: Board
) : EntityCreatePayload<Board>(newEntity), BoardPayload
open class BoardModificationPayload(
    modificationContext: ModificationContext<Board>
): EntityModificationPayload<Board>(modificationContext), BoardPayload
open class BoardDeletePayload(
    deletedEntityId: UUID,
    deletedEntity: Board? = null
): EntityDeletePayload<UUID, Board>(deletedEntityId, deletedEntity), BoardPayload

interface BoardEvent<out T>: EntityEvent<Board, T> where T : BoardPayload

interface BoardCreateEvent: EntityCreateEvent<Board, BoardCreatePayload>, BoardEvent<BoardCreatePayload>
open class BoardCreateEventImp(payload: BoardCreatePayload): GenericEntityCreateEvent<Board, BoardCreatePayload>(payload), BoardCreateEvent

interface BoardModificationEvent: EntityModifyEvent<Board, BoardModificationPayload>, BoardEvent<BoardModificationPayload>
open class BoardModificationEventImp(payload: BoardModificationPayload): GenericEntityModifyEvent<Board, BoardModificationPayload>(payload), BoardModificationEvent

interface BoardDeleteEvent: EntityDeleteEvent<Board, BoardDeletePayload>, BoardEvent<BoardDeletePayload>
open class BoardDeleteEventImp(payload: BoardDeletePayload): GenericEntityDeleteEvent<Board, BoardDeletePayload>(payload), BoardDeleteEvent

@Service
class BoardEventPublisher(
    applicationEventPublisher: ApplicationEventPublisher
) : EntityEventPublisher<BoardEvent<*>>(applicationEventPublisher) {
    fun publishBoardCreate(board: Board) = dispatch(BoardCreateEventImp(BoardCreatePayload(board)))

    fun publishBoardModification(context: ModificationContext<Board>) = dispatch(BoardModificationEventImp(BoardModificationPayload(context)))

    fun publishBoardDelete(id: UUID) = dispatch(BoardDeleteEventImp(BoardDeletePayload(id)))

    fun publishBoardDelete(board: Board) = dispatch(BoardDeleteEventImp(BoardDeletePayload(board.uuid, board)))
}