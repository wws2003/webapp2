#!/bin/bash
echo "1. Setting environment variables"
source 00_setEnv.sh

# Temporary store password
export pg_pass_file=~/.pgpass
export pg_db_password_line=$PSQL_HOST:$PSQL_PORT:postgres:$PSQL_SETUP_USER:$PSQL_POSTGRES_PASSWORD
echo $pg_db_password_line > $pg_pass_file
chmod 0600 $pg_pass_file

# Set ownership permisison for tablespace directory
sudo chown -R $PSQL_SETUP_USER $PSQL_TABLESPACE_PATH

# Create DB, user
echo "2. Creating DB and user"
psql -h $PSQL_HOST -p $PSQL_PORT -U $PSQL_SETUP_USER -w -f sql/01_create_schema_and_user.sql

# Create tables
echo "3. Creating tables"
psql -h $PSQL_HOST -p $PSQL_PORT -U $PSQL_SETUP_USER -w -f sql/02_create_tables.sql

# Insert initial data
echo "4. Insert initial data"
psql -h $PSQL_HOST -p $PSQL_PORT -U $PSQL_SETUP_USER -w -f sql/03_insert_fixed_data.sql

# Delete handy PostgreSQL password file
rm $pg_pass_file
