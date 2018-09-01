#!/bin/bash
# Set environment variables for dev/test
if [ $# -gt 0 ] && [ "$1" = "-t" ]
then
    echo "1. Setting environment variables for test environment"
	source 00_setEnv_forTest.sh
else
	echo "1. Setting environment variables for dev environment"
	source 00_setEnv.sh
fi

# Temporary store password
mkdir -p $PSQL_PASS_DIR
export pg_pass_file=$PSQL_PASS_DIR/$PSQL_PASS_FILENAME
export pg_db_password_line=$PSQL_HOST:$PSQL_PORT:postgres:$PSQL_SETUP_USER:$PSQL_POSTGRES_PASSWORD
echo $pg_db_password_line > $pg_pass_file
chmod 0600 $pg_pass_file

# Drop tables
echo "2. Dropping tables"
psql -h $PSQL_HOST -p $PSQL_PORT -U $PSQL_SETUP_USER -w -f sql/04_drop_tables.sql -v v1="$PSQL_OPERATION_DB"

# Drop user and DB
echo "3. Dropping user and DB"
psql -h $PSQL_HOST -p $PSQL_PORT -U $PSQL_SETUP_USER -w -f sql/05_drop_user_and_schema.sql -v v1="$PSQL_OPERATION_USER" -v v2="$PSQL_OPERATION_ROLE" -v v3="$PSQL_OPERATION_DB" -v v4="$PSQL_TABLESPACE_NAME"

# Delete tablespace physical directory
if ([ $# -eq 1 ] && [ "$1" = "-f" ]) || ([ $# -eq 2 ] && [ "$2" = "-f" ])
then
    sudo rm -rf $PSQL_TABLESPACE_PATH
fi

# Delete handy PostgreSQL password file
rm $pg_pass_file
