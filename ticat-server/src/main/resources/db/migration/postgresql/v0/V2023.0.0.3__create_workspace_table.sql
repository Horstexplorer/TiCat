START TRANSACTION;

CREATE TYPE WORKSPACE_VISIBILITY AS ENUM ('ANYONE', 'LOGGED_IN_USER', 'MEMBERS_ONLY');

CREATE TYPE WORKSPACE_ACCESS_MODE AS ENUM ('PUBLIC', 'REQUEST_BASED', 'INVITE_ONLY');

CREATE TYPE WORKSPACE_STATUS AS ENUM ('ACTIVE', 'ARCHIVED', 'DELETED');

-- Representing a workspace
CREATE TABLE workspaces
(
    uuid                UUID                  NOT NULL DEFAULT gen_random_uuid(),
    human_id            VARCHAR(20)           NOT NULL,

    created_at          TIMESTAMP             NOT NULL DEFAULT NOW(),
    modified_at         TIMESTAMP             NOT NULL DEFAULT NOW(),

    creator_uuid        UUID                  NOT NULL,
    editor_uuid         UUID                           DEFAULT NULL,

    setting_visibility  WORKSPACE_VISIBILITY  NOT NULL DEFAULT 'MEMBERS_ONLY',
    setting_access_mode WORKSPACE_ACCESS_MODE NOT NULL DEFAULT 'INVITE_ONLY',
    setting_status      WORKSPACE_STATUS      NOT NULL DEFAULT 'ACTIVE',
    setting_locale      VARCHAR(5)                     DEFAULT NULL,

    title               VARCHAR(32)           NOT NULL DEFAULT '',
    description         TEXT                  NOT NULL DEFAULT '',

    PRIMARY KEY (uuid),
    CONSTRAINT unique_human_id
        UNIQUE NULLS NOT DISTINCT (human_id),
    CONSTRAINT uppercase_human_id
        CHECK ( UPPER(human_id) = human_id AND human_id NOT LIKE '%[^A-Z0-9]%' ),
    CONSTRAINT creator_fk
        FOREIGN KEY (creator_uuid)
            REFERENCES users (uuid)
            ON DELETE NO ACTION
            ON UPDATE CASCADE
);

-- Representing historical values of workspace modification

CREATE TABLE workspace_history
(
    uuid                    UUID      NOT NULL    DEFAULT gen_random_uuid(),
    entity_reference_uuid   UUID      NOT NULL,
    version_id              INTEGER   NOT NULL    DEFAULT -1,

    created_at              TIMESTAMP NOT NULL    DEFAULT NOW(),
    editor_uuid             UUID      NOT NULL,

    old_title               VARCHAR(32)           DEFAULT NULL,
    old_description         TEXT                  DEFAULT NULL,
    old_setting_visibility  WORKSPACE_VISIBILITY  DEFAULT NULL,
    old_setting_access_mode WORKSPACE_ACCESS_MODE DEFAULT NULL,
    old_setting_status      WORKSPACE_STATUS      DEFAULT NULL,

    PRIMARY KEY (uuid),
    CONSTRAINT unique_version_id
        UNIQUE (entity_reference_uuid, version_id),
    CONSTRAINT workspace_fk
        FOREIGN KEY (entity_reference_uuid)
            REFERENCES workspaces (uuid)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT editor_fk
        FOREIGN KEY (editor_uuid)
            REFERENCES users (uuid)
            ON DELETE NO ACTION
            ON UPDATE CASCADE
);

-- create trigger to set the ticket id to increasing values based on the board; As seen https://stackoverflow.com/a/34571410
CREATE FUNCTION WORKSPACE_VERSION_ID_INCREMENT_FNT()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
DECLARE
    maxID int := 0;
BEGIN
    SELECT MAX(version_id) + 1
    INTO maxID
    FROM workspace_history
    WHERE workspace_history.entity_reference_uuid = NEW.entity_reference_uuid;
    IF maxID IS NULL THEN
        NEW.version_id := 1;
    ELSE
        NEW.version_id := maxID;
    END IF;
    RETURN (NEW);
END;
$$;

CREATE TRIGGER WORKSPACE_VERSION_ID_INCREMENT
    BEFORE INSERT
    ON workspace_history
    FOR EACH ROW
EXECUTE PROCEDURE WORKSPACE_VERSION_ID_INCREMENT_FNT();

CREATE TYPE AUDIT_WORKSPACE_ACTION AS ENUM ('CREATED_WORKSPACE', 'MODIFIED_WORKSPACE', 'ARCHIVED_WORKSPACE', 'UNARCHIVED_WORKSPACE', 'DELETED_WORKSPACE', 'REVERTED_FROM_HISTORY');

-- audit log for workspace related modifications
CREATE TABLE audit_workspaces
(
    uuid                 UUID                   NOT NULL DEFAULT gen_random_uuid(),
    created_at           TIMESTAMP              NOT NULL DEFAULT NOW(),

    action               AUDIT_WORKSPACE_ACTION NOT NULL,
    action_description   TEXT                            DEFAULT NULL,

    actor_entity_uuid    UUID                            DEFAULT NULL,
    actor_entity_hint    TEXT                            DEFAULT NULL,

    affected_entity_uuid UUID                            DEFAULT NULL,
    affected_entity_hint TEXT                            DEFAULT NULL,

    change_history_uuid  UUID                            DEFAULT NULL,

    PRIMARY KEY (uuid),
    CONSTRAINT actor_entity_fk
        FOREIGN KEY (actor_entity_uuid)
            REFERENCES users (uuid)
            ON DELETE SET DEFAULT
            ON UPDATE CASCADE,
    CONSTRAINT affected_entity_fk
        FOREIGN KEY (affected_entity_uuid)
            REFERENCES workspaces (uuid)
            ON DELETE SET DEFAULT
            ON UPDATE CASCADE,
    CONSTRAINT change_history_fk
        FOREIGN KEY (change_history_uuid)
            REFERENCES workspace_history (uuid)
            ON DELETE SET DEFAULT
            ON UPDATE CASCADE
);

COMMIT;