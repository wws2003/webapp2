#!/bin/bash
# Package web-all
mvn clean package -ff -Dweballonly=true -Dnocomp=true -DskipTests=true -pl web-all

# Check artifacts
./04_checkArtifact.sh

# Deploy after confirming user
read -p "To deploy ? Press y: " RESP
if [ "$RESP" = "y" ]; then
  mvn cargo:redeploy -ff -DskipTests=true -pl web-all
else
  echo "Quit without deployment"
fi
