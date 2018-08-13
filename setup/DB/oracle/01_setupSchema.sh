#!/bin/bash
echo "1. Setting environment variables"
source 00_setEnv.sh

# Create DB, user
echo "2. Creating DB and user"
# sqlplus $ORCL_SETUP_USER/$ORCL_SETUP_PASSWORD@ORCL_CONNECT_IDENTIFIER @sql/01_create_schema_and_user.sql

# Create tables
echo "3. Creating tables"
# sqlplus $ORCL_SETUP_USER/$ORCL_SETUP_PASSWORD@ORCL_CONNECT_IDENTIFIER @sql/02_create_tables.sql

# Insert initial data
echo "4. Insert initial data"
# sqlplus $ORCL_SETUP_USER/$ORCL_SETUP_PASSWORD@ORCL_CONNECT_IDENTIFIER @sql/03_insert_fixed_data.sql

