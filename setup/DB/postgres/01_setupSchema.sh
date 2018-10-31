#!/bin/bash
# Set environment variables for dev/test
if [ $# -gt 0 ]
then
    if [ "$1" = "-t" ]
    then
        echo "1. Setting environment variables for test environment"
		source 00_setEnv_forTest.sh
    fi
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

# Create and set ownership permisison for tablespace directory (for Windows, currently have to use Folder properties setting to do grant access for Network Service user)
mkdir -p $PSQL_TABLESPACE_PATH
sudo chown -R $PSQL_SETUP_USER $PSQL_TABLESPACE_PATH

# Create DB, user
echo "2. Creating DB and user"
psql -h $PSQL_HOST -p $PSQL_PORT -U $PSQL_SETUP_USER -w -f sql/01_create_schema_and_user.sql -v v1="$PSQL_TABLESPACE_NAME" -v v2="'$PSQL_TABLESPACE_PATH'" -v v3="$PSQL_OPERATION_DB" -v v4="$PSQL_OPERATION_ROLE" -v v5="$PSQL_OPERATION_USER" -v v6="'$PSQL_OPERATION_USER_PASSWORD'"

# Create tables
echo "3. Creating tables"
psql -h $PSQL_HOST -p $PSQL_PORT -U $PSQL_SETUP_USER -w -f sql/02_create_tables.sql -v v1="$PSQL_OPERATION_DB" -v v2="$PSQL_OPERATION_ROLE"

# Insert initial data
echo "4. Insert initial data"
psql -h $PSQL_HOST -p $PSQL_PORT -U $PSQL_SETUP_USER -w -f sql/03_insert_fixed_data.sql -v v1="$PSQL_OPERATION_DB"

# Delete handy PostgreSQL password file
rm $pg_pass_file

# Finish
echo "Finish schema setup"