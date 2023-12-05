#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
	CREATE USER keycloak WITH PASSWORD 'keycloaksupersecret';
	CREATE DATABASE keycloak WITH OWNER keycloak;

	CREATE USER ticat WITH PASSWORD 'ticatsupersecret';
  CREATE DATABASE ticat WITH OWNER ticat;
EOSQL