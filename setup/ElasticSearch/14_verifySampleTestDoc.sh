#!/bin/bash
# Set environment variables for test
source 00_setEnv_forTest.sh
echo "To verify created document in @$ES_HOST:$ES_PORT/"

# Show document with id = 1 in the created index
curl -X GET "$ES_HOST:$ES_PORT/$ES_INDEX_NAME/$ES_MAPPING_NAME/_search?pretty"

echo ""
