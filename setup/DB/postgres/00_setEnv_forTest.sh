# Detect postgres patha based on OS
if [ "$(uname)" == "Darwin" ]; then
    # Do something under Mac OS X platform
	export PSQL_PATH=/Library/PostgreSQL/10/bin/
	export PSQL_PASS_DIR=~
	export PSQL_PASS_FILENAME=.pgpass
	# Must be absolute path
	export PSQL_TABLESPACE_PATH=$PWD/dataspace_fortest
	# Glassfish containing folder
	export usr_glassfish_home_prefix=$HOME/servers
elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
    # Do something under GNU/Linux platform
	export PSQL_PATH="TODO"
	export PSQL_PASS_DIR=~
	export PSQL_PASS_FILENAME=.pgpass
	# Must be absolute path
	export PSQL_TABLESPACE_PATH=$PWD/dataspace_fortest
	# Glassfish containing folder
	export usr_glassfish_home_prefix=$HOME/servers
elif [ "$(expr substr $(uname -s) 1 10)" == "MINGW32_NT" ]; then
    # Do something under 32 bits Windows NT platform
	export PSQL_PATH="TODO"
	export PSQL_PASS_DIR=~
	export PSQL_PASS_FILENAME=.pgpass
	# Must be absolute path
	export PSQL_TABLESPACE_PATH=$PWD/dataspace_fortest
	# Glassfish containing folder
	export usr_glassfish_home_prefix=$HOME/servers
elif [ "$(expr substr $(uname -s) 1 10)" == "MINGW64_NT" ]; then
    # Do something under 64 bits Windows NT platform
	export PSQL_PATH="/c/Program Files/PostgreSQL/10/bin/"
	export PSQL_PASS_DIR=$APPDATA/postgresql
	export PSQL_PASS_FILENAME=pgpass.conf
	# Must be absolute path with / as delimeter
	export PSQL_TABLESPACE_PATH='C:/Users/trungpt/lab/mvn_modules/sample2/setup/DB/postgres/dataspace_fortest'
	# Glassfish containing folder
	export usr_glassfish_home_prefix=$HOME/servers/glassfish-4.1.2/
fi

export PATH=$PATH:$PSQL_PATH
export PSQL_HOST=localhost
export PSQL_PORT=5432
export PSQL_SETUP_USER=postgres
export PSQL_POSTGRES_PASSWORD=pass001

#---------------Below values also to be passed into SQL statements as arguments
export PSQL_TABLESPACE_NAME=mendel_tbsp_fortest
export PSQL_OPERATION_ROLE=mendel_role_fortest
export PSQL_OPERATION_USER=mendel_user_fortest
export PSQL_OPERATION_USER_PASSWORD=pass001
export PSQL_OPERATION_DB=mendel_db_fortest

# PostgreSQL driver
export PSQL_DRIVER_JAR_NAME=postgresql-9.3-1102-jdbc41.jar

# Glassfish bin folder
export usr_glassfish_bin_path=$usr_glassfish_home_prefix/glassfish4/bin

# Glassfish domain lib folder
export usr_glassfish_domain_lib_path=$usr_glassfish_home_prefix/glassfish4/glassfish/domains/domain1/lib/

# Glassfish connection pool name
export glf_connection_pool_name=glf_mendel_connection_pool
export glf_data_source=glf_mendel_connection_datasource
#---XA datasource is reserved
export glf_data_xasource=glf_mendel_connection_xadatasource
