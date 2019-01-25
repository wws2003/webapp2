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

echo "To verify all indexes in @$ES_HOST:$ES_PORT/"

# Show all index
curl -X GET "$ES_HOST:$ES_PORT/_cat/indices"

# Get detailed information
echo "-------------Settings----------------"
curl -X GET "$ES_HOST:$ES_PORT/$ES_INDEX_NAME/_settings?pretty"
echo ""

echo "-------------Mappings----------------"
curl -X GET "$ES_HOST:$ES_PORT/$ES_INDEX_NAME/_mappings?pretty"
echo ""
