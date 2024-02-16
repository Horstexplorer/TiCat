START TRANSACTION;

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

COMMIT;