export PSQL_PATH=/Library/PostgreSQL/10/bin/
export PATH=$PATH:$PSQL_PATH
export PSQL_TABLESPACE_PATH=/Users/wws2003/Desktop/Apps/WEBAPPS/Mendel/lab/multi-modules/R2/setup/DB/postgres/dataspace
export PSQL_HOST=localhost
export PSQL_PORT=5432
export PSQL_SETUP_USER=postgres
export PSQL_POSTGRES_PASSWORD=pass001

#---------------Below values also hard-coded in SQL file. TODO Better solution
export PSQL_OPERATION_USER=mendel_user
export PSQL_OPERATION_USER_PASSWORD=pass001
export PSQL_OPERATION_DB=mendel_db

# Glassfish containing folder
export usr_glassfish_home_prefix=$HOME/servers

# Glassfish bin folder
export usr_glassfish_bin_path=$usr_glassfish_home_prefix/glassfish4/bin

# Glassfish connection pool name
export glf_connection_pool_name=glf_hrsample_connection_pool
export glf_data_source=glf_hrsample_connection_datasource
#---XA datasource is reserved
export glf_data_xasource=glf_hrsample_connection_xadatasource
