START TRANSACTION;

CREATE TYPE USER_ACCOUNT_TYPE AS ENUM ('SYSTEM', 'ADMIN', 'DEFAULT');

CREATE TYPE USER_SETTING_WORKSPACE_INVITATION_ORIGIN AS ENUM ('ANYONE', 'NOBODY');

CREATE TYPE USER_SETTING_MESSAGE_ORIGIN AS ENUM ('ANYONE', 'WORKSPACE_MEMBER', 'NOBODY');

CREATE TYPE USER_SETTING_STATUS AS ENUM ('ACTIVE', 'DISABLED');

-- Representing a user entity
-- Used to store additional configurations and permissions away from the auth provider
CREATE TABLE users
(
    uuid                                              UUID                                     NOT NULL DEFAULT gen_random_uuid(),
    auth_subject_reference                            TEXT                                              DEFAULT NULL,

    created_at                                        TIMESTAMP                                NOT NULL DEFAULT NOW(),
    modified_at                                       TIMESTAMP                                NOT NULL DEFAULT NOW(),

    first_name                                        VARCHAR(64)                                       DEFAULT NULL,
    last_name                                         VARCHAR(64)                                       DEFAULT NULL,
    display_name                                      VARCHAR(64)                              NOT NULL,

    email                                             VARCHAR(120)                                      DEFAULT NULL,
    account_type                                      USER_ACCOUNT_TYPE                        NOT NULL DEFAULT 'DEFAULT',

    permission_can_create_new_workspaces              BOOLEAN                                  NOT NULL DEFAULT false,
    permission_can_request_workspace_access           BOOLEAN                                  NOT NULL DEFAULT false,
    permission_can_send_messages_to_users             BOOLEAN                                  NOT NULL DEFAULT false,

    setting_receive_workspace_invitations_from_origin USER_SETTING_WORKSPACE_INVITATION_ORIGIN NOT NULL DEFAULT 'ANYONE',
    setting_receive_messages_from_origin              USER_SETTING_MESSAGE_ORIGIN              NOT NULL DEFAULT 'WORKSPACE_MEMBER',
    setting_status                                    USER_SETTING_STATUS                      NOT NULL DEFAULT 'ACTIVE',
    setting_locale                                    VARCHAR(5)                                        DEFAULT NULL,

    PRIMARY KEY (uuid),
    CONSTRAINT unique_auth_subject_reference
        UNIQUE NULLS DISTINCT (auth_subject_reference)
);

-- adds placeholder users
INSERT INTO users (uuid, display_name, account_type, setting_receive_workspace_invitations_from_origin,
                   setting_receive_messages_from_origin, setting_status)
VALUES ('00000000-0000-4000-0000-000000000000', 'System', 'SYSTEM', 'NOBODY', 'NOBODY',
        'DISABLED'), -- placeholder for system user
       ('00000000-0000-4000-0000-000000000002', 'Guest', 'SYSTEM', 'NOBODY', 'NOBODY',
        'ACTIVE'); -- placeholder for anonymous auth, may be enabled when required

-- Representing a known jwt entity to reduce updates of the user entity
CREATE TABLE user_auth_cache
(
    uuid                   UUID      NOT NULL DEFAULT gen_random_uuid(),

    auth_subject_reference TEXT      NOT NULL,
    auth_identifier        TEXT      NOT NULL,

    created_at             TIMESTAMP NOT NULL DEFAULT NOW(),
    predicted_expiry       TIMESTAMP NOT NULL,

    PRIMARY KEY (uuid),
    CONSTRAINT user_fk
        FOREIGN KEY (auth_subject_reference)
            REFERENCES users (auth_subject_reference)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);


CREATE TYPE AUDIT_USER_ACTION AS ENUM ('UPDATED_USER_DETAILS', 'MODIFIED_SETTINGS', 'MODIFIED_PERMISSIONS');

-- audit log for user entity related modifications
CREATE TABLE audit_users
(
    uuid                 UUID              NOT NULL DEFAULT gen_random_uuid(),
    created_at           TIMESTAMP         NOT NULL DEFAULT NOW(),

    action               AUDIT_USER_ACTION NOT NULL,
    action_description   TEXT                       DEFAULT NULL,

    actor_entity_uuid    UUID                       DEFAULT NULL,
    actor_entity_hint    TEXT                       DEFAULT NULL,

    affected_entity_uuid UUID                       DEFAULT NULL,
    affected_entity_hint TEXT                       DEFAULT NULL,

    PRIMARY KEY (uuid),
    CONSTRAINT actor_entity_fk
        FOREIGN KEY (actor_entity_uuid)
            REFERENCES users (uuid)
            ON DELETE SET DEFAULT
            ON UPDATE CASCADE,
    CONSTRAINT affected_entity_fk
        FOREIGN KEY (affected_entity_uuid)
            REFERENCES users (uuid)
            ON DELETE SET DEFAULT
            ON UPDATE CASCADE
);


COMMIT;