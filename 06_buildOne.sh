#!/bin/bash

# Valid arguments: 
# 06_buildOne.sh module_code -> Build single module
# 06_buildOne.sh 1 module_code -> Build the specified module along with web-common
# Module code map
# 2: web-auth
# 3: web-admin
# 4: web-user
# 5: web-search

# Check arguments
if [ $# -lt 1 -o $# -gt 2 ]
then
	echo "Invalid arugments"
	exit -1
fi

# Build and install web-common if needed
if [ $# -eq 2 -a $1 -eq 1 ]
then
	echo "======================================To build web-common======================================"
    #mvn clean install -pl web-common -am
fi

# Build specified module
module='web-auth'

case "$2" in
	"2" ) module='web-auth'
		;;
	"3" ) module='web-admin'
		;;
	"4" ) module='web-user'
		;;
esac

echo "======================================To build "$module"======================================"
mvn clean package -DskipTests=true -pl $module -am
mvn war:war -DskipTests=true -pl web-common,$module,web-all

# Check artifacts
./04_checkArtifact.sh

# Deploy after confirming user
read -p "To deploy ? Press y: " RESP
if [ "$RESP" = "y" ]; then
	mvn integration-test -DskipTests=true -pl web-all
else
 	echo "Quit without deployment"
fi
