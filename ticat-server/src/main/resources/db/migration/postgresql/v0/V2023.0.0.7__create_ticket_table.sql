START TRANSACTION;

CREATE TYPE TICKET_STATUS AS ENUM ('ACTIVE', 'ARCHIVED', 'DELETED');

CREATE TABLE tickets
(
    ticket_uuid              UUID          NOT NULL DEFAULT gen_random_uuid(),
    board_uuid               UUID          NOT NULL,
    ticket_series_id         INTEGER       NOT NULL DEFAULT -1,

    created_at               TIMESTAMP     NOT NULL DEFAULT NOW(),
    modified_at              TIMESTAMP     NOT NULL DEFAULT NOW(),

    creator_uuid             UUID          NOT NULL,
    editor_uuid              UUID                   DEFAULT NULL,

    title                    VARCHAR(64)   NOT NULL DEFAULT '',
    content                  TEXT          NOT NULL DEFAULT '',

    setting_status           TICKET_STATUS NOT NULL DEFAULT 'ACTIVE',
    setting_board_stage_uuid UUID                   DEFAULT NULL,
    setting_assignee_uuid    UUID                   DEFAULT NULL,

    PRIMARY KEY (ticket_uuid),
    CONSTRAINT board_fk
        FOREIGN KEY (board_uuid)
            REFERENCES boards (board_uuid)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT board_stage_fk
        FOREIGN KEY (setting_board_stage_uuid)
            REFERENCES board_stages (board_stage_uuid)
            ON DELETE SET DEFAULT
            ON UPDATE CASCADE,
    CONSTRAINT ticket_series_id_unique
        UNIQUE (board_uuid, ticket_series_id),
    CONSTRAINT creator_fk
        FOREIGN KEY (creator_uuid)
            REFERENCES users (user_uuid)
            ON DELETE NO ACTION
            ON UPDATE CASCADE,
    CONSTRAINT editor_fk
        FOREIGN KEY (editor_uuid)
            REFERENCES users (user_uuid)
            ON DELETE NO ACTION
            ON UPDATE CASCADE,
    CONSTRAINT assignee_fk
        FOREIGN KEY (setting_assignee_uuid)
            REFERENCES users (user_uuid)
            ON DELETE NO ACTION
            ON UPDATE CASCADE
);

-- create trigger to set the ticket id to increasing values based on the board; As seen https://stackoverflow.com/a/34571410
CREATE FUNCTION TICKET_ID_INCREMENT_FNT()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
DECLARE
    maxID int := 0;
BEGIN
    SELECT MAX(ticket_series_id) + 1 INTO maxID FROM tickets WHERE board_uuid = NEW.board_uuid;
    IF maxID IS NULL THEN
        NEW.ticket_series_id := 1;
    ELSE
        NEW.ticket_series_id := maxID;
    END IF;
    RETURN (NEW);
END;
$$;

CREATE TRIGGER TICKET_ID_INCREMENT
    BEFORE INSERT
    ON tickets
    FOR EACH ROW
EXECUTE PROCEDURE TICKET_ID_INCREMENT_FNT();

CREATE TABLE ticket_history
(
    history_uuid                 UUID      NOT NULL DEFAULT gen_random_uuid(),
    entity_reference_uuid        UUID      NOT NULL,
    created_at                   TIMESTAMP NOT NULL DEFAULT NOW(),

    version_id                   INTEGER   NOT NULL DEFAULT -1,
    editor_uuid                  UUID      NOT NULL,

    old_title                    VARCHAR(64)        DEFAULT NULL,
    old_content                  TEXT               DEFAULT NULL,
    old_setting_status           TICKET_STATUS      DEFAULT NULL,
    old_setting_board_stage_uuid UUID               DEFAULT NULL,
    old_setting_assignee_uuid    UUID               DEFAULT NULL,

    PRIMARY KEY (history_uuid),
    CONSTRAINT ticket_fk
        FOREIGN KEY (entity_reference_uuid)
            REFERENCES tickets (ticket_uuid)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT editor_fk
        FOREIGN KEY (editor_uuid)
            REFERENCES users (user_uuid)
            ON DELETE NO ACTION
            ON UPDATE CASCADE,
    CONSTRAINT assignee_fk
        FOREIGN KEY (old_setting_assignee_uuid)
            REFERENCES users (user_uuid)
            ON DELETE NO ACTION
            ON UPDATE CASCADE
);

-- create trigger to set the version id to increasing values based on the board; As seen https://stackoverflow.com/a/34571410
CREATE FUNCTION TICKET_HISTORY_VERSION_ID_INCREMENT_FNT()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
DECLARE
    maxID int := 0;
BEGIN
    SELECT MAX(version_id) + 1 INTO maxID FROM ticket_history WHERE ticket_history.entity_reference_uuid = NEW.entity_reference_uuid;
    IF maxID IS NULL THEN
        NEW.version_id := 1;
    ELSE
        NEW.version_id := maxID;
    END IF;
    RETURN (NEW);
END;
$$;

CREATE TRIGGER TICKET_HISTORY_VERSION_ID_INCREMENT
    BEFORE INSERT
    ON ticket_history
    FOR EACH ROW
EXECUTE PROCEDURE TICKET_HISTORY_VERSION_ID_INCREMENT_FNT();

CREATE TYPE AUDIT_TICKET_ACTION AS ENUM ('CREATED_TICKET', 'MODIFIED_TICKET_CONTENT', 'MODIFIED_TICKET_SETTINGS', 'ARCHIVED_TICKET', 'UNARCHIVED_TICKET', 'DELETED_TICKET', 'REVERTED_FROM_HISTORY');

-- audit log for ticket related modifications
CREATE TABLE audit_ticket
(
    audit_uuid           UUID                NOT NULL DEFAULT gen_random_uuid(),
    created_at           TIMESTAMP           NOT NULL DEFAULT NOW(),

    action               AUDIT_TICKET_ACTION NOT NULL,
    action_description   TEXT                         DEFAULT NULL,

    actor_entity_uuid    UUID                         DEFAULT NULL,
    actor_entity_hint    TEXT                         DEFAULT NULL,

    affected_entity_uuid UUID                         DEFAULT NULL,
    affected_entity_hint TEXT                         DEFAULT NULL,

    parent_entity_uuid   UUID                         DEFAULT NULL,
    parent_entity_hint   TEXT                         DEFAULT NULL,

    change_history_uuid  UUID                         DEFAULT NULL,

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
            REFERENCES boards (board_uuid)
            ON DELETE SET DEFAULT
            ON UPDATE CASCADE,
    CONSTRAINT change_history_fk
        FOREIGN KEY (change_history_uuid)
            REFERENCES ticket_history (history_uuid)
            ON DELETE SET DEFAULT
            ON UPDATE CASCADE
);

COMMIT;