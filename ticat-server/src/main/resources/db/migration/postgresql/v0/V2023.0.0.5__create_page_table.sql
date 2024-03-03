START TRANSACTION;

CREATE TYPE PAGE_STATUS AS ENUM ('ACTIVE', 'ARCHIVED', 'DELETED');

CREATE TABLE pages
(
    page_uuid                UUID        NOT NULL DEFAULT gen_random_uuid(),
    workspace_uuid           UUID        NOT NULL,

    created_at               TIMESTAMP   NOT NULL DEFAULT NOW(),
    modified_at              TIMESTAMP   NOT NULL DEFAULT NOW(),

    creator_uuid             UUID        NOT NULL,
    editor_uuid              UUID                 DEFAULT NULL,

    title                    VARCHAR(64) NOT NULL DEFAULT '',
    content                  TEXT        NOT NULL DEFAULT '',

    setting_status           PAGE_STATUS NOT NULL DEFAULT 'ACTIVE',
    setting_parent_page_uuid UUID                 DEFAULT NULL,

    PRIMARY KEY (page_uuid),
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
            ON UPDATE CASCADE,
    CONSTRAINT parent_page_fk
        FOREIGN KEY (setting_parent_page_uuid)
            REFERENCES pages (page_uuid)
            ON DELETE SET DEFAULT
            ON UPDATE CASCADE
);

CREATE TABLE page_history
(
    history_uuid                 UUID      NOT NULL DEFAULT gen_random_uuid(),
    entity_reference_uuid        UUID      NOT NULL,
    version_id                   INTEGER   NOT NULL DEFAULT -1,

    created_at                   TIMESTAMP NOT NULL DEFAULT NOW(),
    editor_uuid                  UUID      NOT NULL,

    old_title                    VARCHAR(64)        DEFAULT NULL,
    old_content                  TEXT               DEFAULT NULL,
    old_setting_status           PAGE_STATUS        DEFAULT NULL,
    old_setting_parent_page_uuid UUID               DEFAULT NULL,

    PRIMARY KEY (history_uuid),
    CONSTRAINT page_fk
        FOREIGN KEY (entity_reference_uuid)
            REFERENCES pages (page_uuid)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT editor_fk
        FOREIGN KEY (editor_uuid)
            REFERENCES users (user_uuid)
            ON DELETE NO ACTION
            ON UPDATE CASCADE,
    CONSTRAINT old_parent_page_fk
        FOREIGN KEY (old_setting_parent_page_uuid)
            REFERENCES pages (page_uuid)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

-- create trigger to set the version id to increasing values based on the board; As seen https://stackoverflow.com/a/34571410
CREATE FUNCTION PAGE_HISTORY_VERSION_ID_INCREMENT_FNT()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
DECLARE
    maxID int := 0;
BEGIN
    SELECT MAX(version_id) + 1 INTO maxID FROM page_history WHERE page_history.entity_reference_uuid = NEW.entity_reference_uuid;
    IF maxID IS NULL THEN
        NEW.version_id := 1;
    ELSE
        NEW.version_id := maxID;
    END IF;
    RETURN (NEW);
END;
$$;

CREATE TRIGGER PAGE_HISTORY_VERSION_ID_INCREMENT
    BEFORE INSERT
    ON page_history
    FOR EACH ROW
EXECUTE PROCEDURE PAGE_HISTORY_VERSION_ID_INCREMENT_FNT();


CREATE TYPE AUDIT_PAGE_ACTION AS ENUM ('CREATED_PAGE', 'MODIFIED_PAGE', 'ARCHIVED_PAGE', 'UNARCHIVED_PAGE', 'DELETED_PAGE', 'REVERTED_FROM_HISTORY');

-- audit log for page related modifications
CREATE TABLE audit_pages
(
    audit_uuid           UUID              NOT NULL DEFAULT gen_random_uuid(),
    created_at           TIMESTAMP         NOT NULL DEFAULT NOW(),

    action               AUDIT_PAGE_ACTION NOT NULL,
    action_description   TEXT                       DEFAULT NULL,

    actor_entity_uuid    UUID                       DEFAULT NULL,
    actor_entity_hint    TEXT                       DEFAULT NULL,

    affected_entity_uuid UUID                       DEFAULT NULL,
    affected_entity_hint TEXT                       DEFAULT NULL,

    parent_entity_uuid   UUID                       DEFAULT NULL,
    parent_entity_hint   TEXT                       DEFAULT NULL,

    change_history_uuid  UUID                       DEFAULT NULL,

    PRIMARY KEY (audit_uuid),
    CONSTRAINT actor_entity_fk
        FOREIGN KEY (actor_entity_uuid)
            REFERENCES users (user_uuid)
            ON DELETE SET DEFAULT
            ON UPDATE CASCADE,
    CONSTRAINT affected_entity_fk
        FOREIGN KEY (affected_entity_uuid)
            REFERENCES pages (page_uuid)
            ON DELETE SET DEFAULT
            ON UPDATE CASCADE,
    CONSTRAINT parent_entity_fk
        FOREIGN KEY (parent_entity_uuid)
            REFERENCES workspaces (workspace_uuid)
            ON DELETE SET DEFAULT
            ON UPDATE CASCADE,
    CONSTRAINT change_history_fk
        FOREIGN KEY (change_history_uuid)
            REFERENCES page_history (history_uuid)
            ON DELETE SET DEFAULT
            ON UPDATE CASCADE
);

COMMIT;