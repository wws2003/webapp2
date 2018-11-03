#!/bin/bash

# Valid arguments:
# 06_buildOne.sh module_code -> Build single module
# 06_buildOne.sh 1 module_code -> Build the specified module along with web-common
# Module code map
# 2: web-auth
# 3: web-admin
# 4: web-user
# 5: web-project

# Check arguments
if [ $# -lt 1 -o $# -gt 2 ]
then
	echo "Invalid arugments"
	exit -1
fi

# Build and install web-common if needed
if [ $# -eq 2 -a $1 -eq 1 ]
then
    echo "======================================To build and install web-common======================================"
    mvn clean install -ff -DskipTests=true -pl web-common -am
fi

module_code=2
if [ $# -eq 2 ]
then
	module_code="$2"
else
	module_code="$1"
fi

# Build specified module
module="web-auth"

case $module_code in
	"1" ) module="web-common"
		;;
	"2" ) module="web-auth"
		;;
	"3" ) module="web-admin"
		;;
	"4" ) module="web-user"
		;;
	"5" ) module="web-project"
		;;
esac

echo "======================================To build "$module"======================================"
mvn clean package -ff -DskipTests=true -pl $module -am
if [ "$module" == "web-common" ]
then
	mvn war:war -ff -DskipTests=true -pl web-common,web-all
else
	mvn war:war -ff -DskipTests=true -pl web-common,$module,web-all
fi

# Check artifacts
./04_checkArtifact.sh

# Deploy after confirming user
read -p "To deploy ? Press y: " RESP
if [ "$RESP" = "y" ]
then
	mvn integration-test -ff -DskipTests=true -pl web-all
else
 	echo "Quit without deployment"
fi
