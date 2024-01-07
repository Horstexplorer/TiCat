#!/bin/bash
set -e

echo "Running init-user-db.sh as $POSTGRESQL_USERNAME on $POSTGRESQL_DATABASE"

export PGPASSWORD="$POSTGRESQL_PASSWORD"
psql -v ON_ERROR_STOP=1 --username "$POSTGRESQL_USERNAME" --dbname "$POSTGRESQL_DATABASE" <<-EOSQL
	CREATE USER keycloak WITH PASSWORD 'keycloaksupersecret';
	CREATE DATABASE keycloak WITH OWNER keycloak;

	CREATE USER ticat WITH PASSWORD 'ticatsupersecret';
  CREATE DATABASE ticat WITH OWNER ticat;
EOSQL

echo "Finished executing init-user-db.sh"