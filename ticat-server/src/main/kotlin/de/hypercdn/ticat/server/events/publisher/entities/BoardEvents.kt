package de.hypercdn.ticat.server.events.publisher.entities

import de.hypercdn.ticat.server.data.sql.entities.board.Board
import de.hypercdn.ticat.server.events.publisher.base.*
import java.util.UUID

typealias BoardPayload = EntityPayload<Board>
typealias BoardCreatePayload = EntityCreatePayload<Board>
typealias BoardModificationPayload = EntityModificationPayload<Board>
typealias BoardDeletePayload = EntityDeletePayload<UUID, Board>

interface BoardEvent<T>: EntityEvent<T> where T : BoardPayload
open class GenericBoardEvent<T>(payload: T): GenericEntityEvent<T>(payload), BoardEvent<T> where T : BoardPayload

interface BoardCreateEvent: EntityCreateEvent<Board>, BoardEvent<BoardCreatePayload>
class BoardCreateEventImp(payload: BoardCreatePayload): GenericBoardEvent<BoardCreatePayload>(payload), BoardCreateEvent

interface BoardModificationEvent: EntityModificationEvent<Board>, BoardEvent<BoardModificationPayload>
class BoardModificationEventImp(payload: BoardModificationPayload): GenericBoardEvent<BoardModificationPayload>(payload), BoardModificationEvent

interface BoardDeleteEvent: EntityDeleteEvent<UUID, Board>, BoardEvent<BoardDeletePayload>
class BoardDeleteEventImp(payload: BoardDeletePayload): GenericBoardEvent<BoardDeletePayload>(payload), BoardDeleteEvent
