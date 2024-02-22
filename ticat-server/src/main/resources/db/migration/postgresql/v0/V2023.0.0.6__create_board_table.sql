START TRANSACTION;

CREATE TYPE BOARD_STATUS AS ENUM ('ACTIVE', 'ARCHIVED', 'DELETED');

CREATE TABLE boards
(
    board_uuid     UUID         NOT NULL DEFAULT gen_random_uuid(),
    workspace_uuid UUID         NOT NULL,

    created_at     TIMESTAMP    NOT NULL DEFAULT NOW(),
    modified_at    TIMESTAMP    NOT NULL DEFAULT NOW(),

    creator_uuid   UUID         NOT NULL,
    editor_uuid    UUID                  DEFAULT NULL,

    title          VARCHAR(64)  NOT NULL DEFAULT '',
    setting_status BOARD_STATUS NOT NULL DEFAULT 'ACTIVE',

    PRIMARY KEY (board_uuid),
    CONSTRAINT workspace_fk
        FOREIGN KEY (workspace_uuid)
            REFERENCES workspaces (workspace_uuid)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT creator_fk
        FOREIGN KEY (creator_uuid)
            REFERENCES users (user_uuid)
            ON DELETE NO ACTION
            ON UPDATE CASCADE,
    CONSTRAINT editor_fk
        FOREIGN KEY (editor_uuid)
            REFERENCES users (user_uuid)
            ON DELETE NO ACTION
            ON UPDATE CASCADE
);

CREATE TYPE BOARD_STAGE_STATUS AS ENUM ('ACTIVE', 'ARCHIVED', 'DELETED');

CREATE TABLE board_stages
(
    board_stage_uuid    UUID               NOT NULL DEFAULT gen_random_uuid(),
    board_uuid          UUID               NOT NULL,

    created_at          TIMESTAMP          NOT NULL DEFAULT NOW(),
    modified_at         TIMESTAMP          NOT NULL DEFAULT NOW(),

    creator_uuid        UUID               NOT NULL,
    editor_uuid         UUID                        DEFAULT NULL,

    title               VARCHAR(64)        NOT NULL DEFAULT '',
    setting_index_value INTEGER            NOT NULL DEFAULT 0,
    setting_status      BOARD_STAGE_STATUS NOT NULL DEFAULT 'ACTIVE',

    PRIMARY KEY (board_stage_uuid),
    CONSTRAINT board_fk
        FOREIGN KEY (board_uuid)
            REFERENCES boards (board_uuid)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT creator_fk
        FOREIGN KEY (creator_uuid)
            REFERENCES users (user_uuid)
            ON DELETE NO ACTION
            ON UPDATE CASCADE,
    CONSTRAINT editor_fk
        FOREIGN KEY (editor_uuid)
            REFERENCES users (user_uuid)
            ON DELETE NO ACTION
            ON UPDATE CASCADE
);


CREATE TYPE AUDIT_BOARD_ACTION AS ENUM ('CREATED_BOARD', 'MODIFIED_BOARD', 'ARCHIVED_BOARD', 'UNARCHIVED_BOARD', 'DELETED_BOARD');

-- audit log for board related modifications
CREATE TABLE audit_boards
(
    audit_uuid           UUID              NOT NULL DEFAULT gen_random_uuid(),
    created_at           TIMESTAMP         NOT NULL DEFAULT NOW(),

    action               AUDIT_BOARD_ACTION NOT NULL,
    action_description   TEXT                       DEFAULT NULL,

    actor_entity_uuid    UUID                       DEFAULT NULL,
    actor_entity_hint    TEXT                       DEFAULT NULL,

    affected_entity_uuid UUID                       DEFAULT NULL,
    affected_entity_hint TEXT                       DEFAULT NULL,

    parent_entity_uuid   UUID                       DEFAULT NULL,
    parent_entity_hint   TEXT                       DEFAULT NULL,

    PRIMARY KEY (audit_uuid),
    CONSTRAINT actor_entity_fk
        FOREIGN KEY (actor_entity_uuid)
            REFERENCES users (user_uuid)
            ON DELETE SET DEFAULT
            ON UPDATE CASCADE,
    CONSTRAINT affected_entity_fk
        FOREIGN KEY (affected_entity_uuid)
            REFERENCES boards (board_uuid)
            ON DELETE SET DEFAULT
            ON UPDATE CASCADE,
    CONSTRAINT parent_entity_fk
        FOREIGN KEY (parent_entity_uuid)
            REFERENCES workspaces (workspace_uuid)
            ON DELETE SET DEFAULT
            ON UPDATE CASCADE
);

COMMIT;