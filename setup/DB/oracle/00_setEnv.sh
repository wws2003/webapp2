export SQPL_PATH=c/Users/trungpt/ecood/platform/instantclient_12_2
export PATH=$PATH:$ORCL_PATH
export ORCL_TABLESPACE_PATH=/c/Users/trungpt/lab/mvn_modules/sample2/setup/DB/oracle/dataspace
export ORCL_HOST=localhost
export ORCL_PORT=5432
export ORCL_SETUP_USER=dba
export ORCL_SETUP_PASSWORD=pass001
export ORCL_SID=mendel.dbsv
# Shortcut
export ORCL_CONNECT_IDENTIFIER=%ORCL_HOST%:%ORCL_PORT%/%ORCL_SID%

#---------------Below values also hard-coded in SQL file. TODO Better solution
export ORCL_OPERATION_USER=mendel_user
export ORCL_OPERATION_USER_PASSWORD=pass001
export ORCL_OPERATION_DB=mendel_db

# Glassfish containing folder
export usr_glassfish_home_prefix=$HOME/servers

# Glassfish bin folder
export usr_glassfish_bin_path=$usr_glassfish_home_prefix/glassfish4/bin

# Glassfish connection pool name
export glf_connection_pool_name=glf_mendel_connection_pool
export glf_data_source=glf_mendel_connection_datasource
#---XA datasource is reserved
export glf_data_xasource=glf_mendel_connection_xadatasource
