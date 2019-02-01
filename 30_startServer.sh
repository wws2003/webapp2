# Load basic setting
source 00_setEnv.sh

cd $SERVER_PATH
./asadmin start-domain --verbose --debug
