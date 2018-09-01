#!/bin/bash
# Reset all DB stuffs by drop-then-setup

echo "----------------------------1. Dropping datasources----------------------------"
./03_dropDataSources.sh

echo "----------------------------2. Dropping schema----------------------------"
./04_dropSchema.sh -f

# Re-setup after confirming user
read -p "To setup again ? Press y: " RESP
if [ "$RESP" = "y" ]; then
 	echo "----------------------------3. Setting up schema----------------------------"
 	./01_setupSchema.sh
	echo "----------------------------4. Creating datasources----------------------------"
	./02_setupDataSources.sh
else
 	echo "DB stuffs dropped completely"
fi
