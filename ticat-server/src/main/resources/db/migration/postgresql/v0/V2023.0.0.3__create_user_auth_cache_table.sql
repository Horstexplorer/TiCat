START TRANSACTION;

-- Representing a known jwt entity to reduce updates of the user entity
CREATE TABLE user_auth_cache
(
    auth_subject_reference TEXT      NOT NULL,
    auth_identifier        TEXT      NOT NULL,

    created_at             TIMESTAMP NOT NULL DEFAULT NOW(),
    predicted_expiry       TIMESTAMP NOT NULL,

    PRIMARY KEY (auth_subject_reference, auth_identifier),
    CONSTRAINT user_fk
        FOREIGN KEY (auth_subject_reference)
            REFERENCES users (auth_subject_reference)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

COMMIT;