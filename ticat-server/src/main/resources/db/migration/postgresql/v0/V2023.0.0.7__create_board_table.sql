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

COMMIT;