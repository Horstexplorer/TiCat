START TRANSACTION;

CREATE TYPE WORKSPACE_MEMBER_MEMBERSHIP_STATUS AS ENUM ('MEMBERSHIP_REQUESTED', 'MEMBERSHIP_OFFERED', 'MEMBERSHIP_GRANTED', 'MEMBERSHIP_DENIED');

-- DENY: No Access
-- CAN_VIEW: Can view existing entities
-- CAN_VIEW_CREATE: Can view existing entities. Can create new entities. Can modify, archive and delete owned entities unless blocked by parent entity.
-- CAN_VIEW_CREATE_EDIT: Can view existing entities. Can create new entities. Can modify, archive and delete owned entities unless blocked by parent entity. Can modify, archive not owned entities unless blocked by parent entity.
-- CAN_VIEW_CREATE_EDIT_DELETE: Can view existing entities. Can create new entities. Can modify, archive and delete owned entities unless blocked by parent entity. Can modify, archive or delete not owned entities unless blocked by parent entity.
CREATE TYPE WORKSPACE_MEMBER_PAGE_PERMISSION AS ENUM ('DENY', 'CAN_VIEW', 'CAN_VIEW_CREATE', 'CAN_VIEW_CREATE_EDIT', 'CAN_VIEW_CREATE_EDIT_DELETE');
-- DENY: No Access
-- CAN_VIEW: Can view existing entities
-- CAN_VIEW_CREATE: Can view existing entities. Can create new entities. Can modify, archive and delete owned entities unless blocked by parent entity.
-- CAN_VIEW_CREATE_EDIT: Can view existing entities. Can create new entities. Can modify, archive and delete owned entities unless blocked by parent entity. Can modify, archive not owned entities unless blocked by parent entity.
-- CAN_VIEW_CREATE_EDIT_DELETE: Can view existing entities. Can create new entities. Can modify, archive and delete owned entities unless blocked by parent entity. Can modify, archive or delete not owned entities unless blocked by parent entity.
CREATE TYPE WORKSPACE_MEMBER_BOARD_PERMISSION AS ENUM ('DENY', 'CAN_VIEW', 'CAN_VIEW_CREATE', 'CAN_VIEW_CREATE_EDIT', 'CAN_VIEW_CREATE_EDIT_DELETE');
-- DENY: No Access
-- CAN_VIEW: Can view existing entities
-- CAN_VIEW_CREATE: Can view existing entities. Can create new entities. Can modify, archive and delete owned entities unless blocked by parent entity.
-- CAN_VIEW_CREATE_EDIT: Can view existing entities. Can create new entities. Can modify, archive and delete owned entities unless blocked by parent entity. Can modify, archive not owned entities unless blocked by parent entity.
-- CAN_VIEW_CREATE_EDIT_DELETE: Can view existing entities. Can create new entities. Can modify, archive and delete owned entities unless blocked by parent entity. Can modify, archive or delete not owned entities unless blocked by parent entity.
CREATE TYPE WORKSPACE_MEMBER_TICKET_PERMISSION AS ENUM ('DENY', 'CAN_VIEW', 'CAN_VIEW_CREATE', 'CAN_VIEW_CREATE_EDIT', 'CAN_VIEW_CREATE_EDIT_DELETE');
-- DENY: No Access
-- CAN_VIEW: Can view existing entities
-- CAN_VIEW_MANAGE_MEMBERS: Can view existing entities. Can manage entities.
-- CAN_ADMINISTRATE: Can view existing entities. Can manage entities. Overrides all other permissions within sub scopes.
CREATE TYPE WORKSPACE_MEMBER_WORKSPACE_PERMISSION AS ENUM ('DENY', 'CAN_VIEW', 'CAN_VIEW_MANAGE_MEMBERS', 'CAN_ADMINISTRATE');

-- Representing a user within the context of a workspace
CREATE TABLE workspace_members
(
    uuid                 UUID                                  NOT NULL DEFAULT gen_random_uuid(),
    workspace_uuid       UUID                                  NOT NULL,
    user_uuid            UUID                                  NOT NULL,

    created_at           TIMESTAMP                             NOT NULL DEFAULT NOW(),
    modified_at          TIMESTAMP                             NOT NULL DEFAULT NOW(),

    membership_status    WORKSPACE_MEMBER_MEMBERSHIP_STATUS    NOT NULL DEFAULT 'MEMBERSHIP_DENIED',

    permission_pages     WORKSPACE_MEMBER_PAGE_PERMISSION      NOT NULL DEFAULT 'CAN_VIEW',
    permission_boards    WORKSPACE_MEMBER_BOARD_PERMISSION     NOT NULL DEFAULT 'CAN_VIEW',
    permission_tickets   WORKSPACE_MEMBER_TICKET_PERMISSION    NOT NULL DEFAULT 'CAN_VIEW',
    permission_workspace WORKSPACE_MEMBER_WORKSPACE_PERMISSION NOT NULL DEFAULT 'CAN_VIEW',

    PRIMARY KEY (uuid),
    CONSTRAINT unique_user_workspace_combination
        UNIQUE (workspace_uuid, user_uuid),
    CONSTRAINT user_fk
        FOREIGN KEY (user_uuid)
            REFERENCES users (uuid)
            ON DELETE NO ACTION
            ON UPDATE CASCADE,
    CONSTRAINT workspace_fk
        FOREIGN KEY (workspace_uuid)
            REFERENCES workspaces (uuid)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TYPE AUDIT_WORKSPACE_MEMBER_ACTION AS ENUM ('MEMBERSHIP_REQUESTED', 'MEMBERSHIP_OFFERED', 'MEMBERSHIP_GRANTED', 'MEMBERSHIP_DENIED', 'MODIFIED_PERMISSIONS');

-- audit log for workspace member related modifications
CREATE TABLE audit_workspace_members
(
    uuid                 UUID                          NOT NULL DEFAULT gen_random_uuid(),
    created_at           TIMESTAMP                     NOT NULL DEFAULT NOW(),

    action               AUDIT_WORKSPACE_MEMBER_ACTION NOT NULL,
    action_description   TEXT                                   DEFAULT NULL,

    actor_entity_uuid    UUID                                   DEFAULT NULL,
    actor_entity_hint    TEXT                                   DEFAULT NULL,

    affected_entity_uuid UUID                                   DEFAULT NULL,
    affected_entity_hint TEXT                                   DEFAULT NULL,

    parent_entity_uuid   UUID                                   DEFAULT NULL,
    parent_entity_hint   TEXT                                   DEFAULT NULL,

    PRIMARY KEY (uuid),
    CONSTRAINT actor_entity_fk
        FOREIGN KEY (actor_entity_uuid)
            REFERENCES users (uuid)
            ON DELETE SET DEFAULT
            ON UPDATE CASCADE,
    CONSTRAINT affected_entity_fk
        FOREIGN KEY (affected_entity_uuid)
            REFERENCES workspace_members (uuid)
            ON DELETE SET DEFAULT
            ON UPDATE CASCADE,
    CONSTRAINT parent_entity_fk
        FOREIGN KEY (parent_entity_uuid)
            REFERENCES workspaces (uuid)
            ON DELETE SET DEFAULT
            ON UPDATE CASCADE
);

COMMIT;