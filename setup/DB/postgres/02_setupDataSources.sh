#!/bin/bash
# Set environment variables
echo "1. Setting environment variables"
source 00_setEnv.sh

# Export path
export PATH=$PATH:$usr_glassfish_bin_path

# Create new  JDBC connection pool
echo "2. Create new JDBC connection pool"
asadmin --interactive=false --echo create-jdbc-connection-pool --datasourceclassname org.postgresql.ds.PGSimpleDataSource --restype javax.sql.DataSource --property user=$PSQL_OPERATION_USER:password=$PSQL_OPERATION_USER_PASSWORD:serverName=$PSQL_HOST:portName=$PSQL_PORT:dataBaseName=$PSQL_OPERATION_DB jdbc/$glf_connection_pool_name

# Create new JDBC datasource
echo "3. Create new JDBC datasource"
asadmin --interactive=false --echo create-jdbc-resource --poolname jdbc/$glf_connection_pool_name jdbc/$glf_data_source

# Check created connection pool and JDBC datasource
echo "4. Listing JDBC connection pools and datasources (for check purpose)"
asadmin list-jdbc-connection-pools
asadmin list-jdbc-resources
