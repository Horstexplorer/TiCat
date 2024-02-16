START TRANSACTION;

CREATE TYPE MESSAGE_STATUS AS ENUM ('ACTIVE', 'ARCHIVED', 'DELETED');

CREATE TABLE messages
(
    message_uuid             UUID          NOT NULL DEFAULT gen_random_uuid(),

    created_at               TIMESTAMP     NOT NULL DEFAULT NOW(),
    modified_at              TIMESTAMP     NOT NULL DEFAULT NOW(),

    sender_uuid              UUID          NOT NULL,

    recipient_workspace_uuid UUID                   DEFAULT NULL,
    recipient_page_uuid      UUID                   DEFAULT NULL,
    recipient_ticket_uuid    UUID                   DEFAULT NULL,
    recipient_user_uuid      UUID                   DEFAULT NULL,

    parent_message_uuid      UUID                   DEFAULT NULL,
    content                  TEXT          NOT NULL DEFAULT '',

    setting_status           MESSAGE_STATUS NOT NULL DEFAULT 'ACTIVE',

    PRIMARY KEY (message_uuid),
    CONSTRAINT sender_fk
        FOREIGN KEY (sender_uuid)
            REFERENCES users (user_uuid)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT recipient_workspace_fk
        FOREIGN KEY (recipient_workspace_uuid)
            REFERENCES workspaces (workspace_uuid)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT recipient_page_fk
        FOREIGN KEY (recipient_page_uuid)
            REFERENCES pages (page_uuid)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT recipient_ticket_fk
        FOREIGN KEY (recipient_ticket_uuid)
            REFERENCES tickets (ticket_uuid)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT recipient_user_fk
        FOREIGN KEY (recipient_user_uuid)
            REFERENCES users (user_uuid)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT parent_message_fk
        FOREIGN KEY (parent_message_uuid)
            REFERENCES messages (message_uuid)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

COMMIT;