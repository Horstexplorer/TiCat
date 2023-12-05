START TRANSACTION;

-- remove entries that are outdated
DELETE
FROM user_auth_cache uac
WHERE uac.predicted_expiry < NOW();

COMMIT;