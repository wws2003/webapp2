#!/bin/bash
# Build and install web-common if needed
if [ $# -gt 0 ]
then
    if [ $1 -eq 1 ]
    then
        mvn clean install -ff -DskipTests=true -pl web-common -am
    fi
fi
# Package web-all
mvn clean package -ff -DskipTests=true -pl web-all -am
# Final package (to include web-common)
mvn war:war -ff -DskipTests=true -pl web-common,web-all 

# Check artifacts
./04_checkArtifact.sh

# Deploy after confirming user
read -p "To deploy ? Press y: " RESP
if [ "$RESP" = "y" ]; then
  mvn cargo:redeploy -ff -DskipTests=true -pl web-all
else
  echo "Quit without deployment"
fi
