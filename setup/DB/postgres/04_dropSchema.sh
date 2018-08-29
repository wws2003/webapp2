#!/bin/bash
echo "1. Setting environment variables"
source 00_setEnv.sh

# Temporary store password
mkdir -p $PSQL_PASS_DIR
export pg_pass_file=$PSQL_PASS_DIR/$PSQL_PASS_FILENAME
export pg_db_password_line=$PSQL_HOST:$PSQL_PORT:postgres:$PSQL_SETUP_USER:$PSQL_POSTGRES_PASSWORD
echo $pg_db_password_line > $pg_pass_file
chmod 0600 $pg_pass_file

# Drop tables
echo "2. Dropping tables"
psql -h $PSQL_HOST -p $PSQL_PORT -U $PSQL_SETUP_USER -w -f sql/04_drop_tables.sql

# Drop user and DB
echo "3. Dropping user and DB"
psql -h $PSQL_HOST -p $PSQL_PORT -U $PSQL_SETUP_USER -w -f sql/05_drop_user_and_schema.sql

# Delete tablespace physical directory
if [ $# -gt 0 ]
then
    if [ $1 -eq 1 ]
    then
        rm -rf $PSQL_TABLESPACE_PATH
    fi
fi

# Delete handy PostgreSQL password file
rm $pg_pass_file