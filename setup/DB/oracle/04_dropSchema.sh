#!/bin/bash
echo "1. Setting environment variables"
source 00_setEnv.sh

# Drop tables
echo "2. Dropping tables"
# sqlplus $ORCL_SETUP_USER/$ORCL_SETUP_PASSWORD@ORCL_CONNECT_IDENTIFIER @sql/04_drop_tables.sql

# Drop user and DB
echo "3. Dropping user and DB"
# sqlplus $ORCL_SETUP_USER/$ORCL_SETUP_PASSWORD@ORCL_CONNECT_IDENTIFIER @sql/05_drop_user_and_schema.sql
