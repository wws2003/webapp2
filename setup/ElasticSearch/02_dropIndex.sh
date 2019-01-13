#!/bin/bash
# Set environment variables for dev/test
if [ $# -gt 0 ]
then
    if [ "$1" = "-t" ]
    then
        echo "1. Setting environment variables for test environment"
		source 00_setEnv_forTest.sh
    fi
else
	echo "1. Setting environment variables for dev environment"
	source 00_setEnv.sh
fi

echo "To delete index @$ES_HOST:$ES_PORT/$ES_INDEX_NAME"

# The simplest case: Single index for the whole application
curl -X DELETE "$ES_HOST:$ES_PORT/$ES_INDEX_NAME"
