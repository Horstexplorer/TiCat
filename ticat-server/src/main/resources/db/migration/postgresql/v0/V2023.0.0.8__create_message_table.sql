START TRANSACTION;

CREATE TYPE MESSAGE_STATUS AS ENUM ('ACTIVE', 'ARCHIVED', 'DELETED');

CREATE TABLE messages
(
    uuid                     UUID           NOT NULL DEFAULT gen_random_uuid(),

    created_at               TIMESTAMP      NOT NULL DEFAULT NOW(),
    modified_at              TIMESTAMP      NOT NULL DEFAULT NOW(),

    sender_uuid              UUID           NOT NULL,

    recipient_workspace_uuid UUID                    DEFAULT NULL,
    recipient_page_uuid      UUID                    DEFAULT NULL,
    recipient_ticket_uuid    UUID                    DEFAULT NULL,
    recipient_user_uuid      UUID                    DEFAULT NULL,

    parent_message_uuid      UUID                    DEFAULT NULL,
    content                  TEXT           NOT NULL DEFAULT '',

    setting_status           MESSAGE_STATUS NOT NULL DEFAULT 'ACTIVE',

    PRIMARY KEY (uuid),
    CONSTRAINT sender_fk
        FOREIGN KEY (sender_uuid)
            REFERENCES users (uuid)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT recipient_workspace_fk
        FOREIGN KEY (recipient_workspace_uuid)
            REFERENCES workspaces (uuid)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT recipient_page_fk
        FOREIGN KEY (recipient_page_uuid)
            REFERENCES pages (uuid)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT recipient_ticket_fk
        FOREIGN KEY (recipient_ticket_uuid)
            REFERENCES tickets (uuid)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT recipient_user_fk
        FOREIGN KEY (recipient_user_uuid)
            REFERENCES users (uuid)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT parent_message_fk
        FOREIGN KEY (parent_message_uuid)
            REFERENCES messages (uuid)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE message_history
(
    uuid                  UUID      NOT NULL DEFAULT gen_random_uuid(),
    entity_reference_uuid UUID      NOT NULL,
    created_at            TIMESTAMP NOT NULL DEFAULT NOW(),
    version_id            INTEGER   NOT NULL DEFAULT -1,
    editor_uuid           UUID      NOT NULL,
    old_content           TEXT               DEFAULT NULL,
    old_setting_status    MESSAGE_STATUS     DEFAULT NULL,

    PRIMARY KEY (uuid),
    CONSTRAINT message_fk
        FOREIGN KEY (entity_reference_uuid)
            REFERENCES messages (uuid)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT editor_fk
        FOREIGN KEY (editor_uuid)
            REFERENCES users (uuid)
            ON DELETE NO ACTION
            ON UPDATE CASCADE
);

-- create trigger to set the version id to increasing values in relationship to a message entity; As seen https://stackoverflow.com/a/34571410
CREATE FUNCTION MESSAGE_HISTORY_VERSION_ID_INCREMENT_FNT()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
DECLARE
    maxID int := 0;
BEGIN
    SELECT MAX(version_id) + 1 INTO maxID FROM message_history WHERE message_history.entity_reference_uuid = NEW.entity_reference_uuid;
    IF maxID IS NULL THEN
        NEW.version_id := 1;
    ELSE
        NEW.version_id := maxID;
    END IF;
    RETURN (NEW);
END;
$$;

CREATE TRIGGER MESSAGE_HISTORY_VERSION_ID_INCREMENT
    BEFORE INSERT
    ON message_history
    FOR EACH ROW
EXECUTE PROCEDURE MESSAGE_HISTORY_VERSION_ID_INCREMENT_FNT();

CREATE TYPE AUDIT_MESSAGE_ACTION AS ENUM ('CREATED_MESSAGE', 'MODIFIED_MESSAGE_CONTENT', 'ARCHIVED_MESSAGE', 'DELETED_MESSAGE', 'REVERTED_FROM_HISTORY');

-- audit log for message related modifications
CREATE TABLE audit_messages
(
    uuid                 UUID                 NOT NULL DEFAULT gen_random_uuid(),
    created_at           TIMESTAMP            NOT NULL DEFAULT NOW(),

    action               AUDIT_MESSAGE_ACTION NOT NULL,
    action_description   TEXT                          DEFAULT NULL,

    actor_entity_uuid    UUID                          DEFAULT NULL,
    actor_entity_hint    TEXT                          DEFAULT NULL,

    affected_entity_uuid UUID                          DEFAULT NULL,
    affected_entity_hint TEXT                          DEFAULT NULL,

    change_history_uuid  UUID                          DEFAULT NULL,

    PRIMARY KEY (uuid),
    CONSTRAINT actor_entity_fk
        FOREIGN KEY (actor_entity_uuid)
            REFERENCES users (uuid)
            ON DELETE SET DEFAULT
            ON UPDATE CASCADE,
    CONSTRAINT affected_entity_fk
        FOREIGN KEY (affected_entity_uuid)
            REFERENCES messages (uuid)
            ON DELETE SET DEFAULT
            ON UPDATE CASCADE,
    CONSTRAINT change_history_fk
        FOREIGN KEY (change_history_uuid)
            REFERENCES message_history (uuid)
            ON DELETE SET DEFAULT
            ON UPDATE CASCADE
);

COMMIT;