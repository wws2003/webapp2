#!/bin/bash
# Set environment variables for test
source 00_setEnv_forTest.sh

echo "To create document in index @$ES_HOST:$ES_PORT/$ES_INDEX_NAME"

# The simplest case: Single index for the whole application
# Document id = 1
curl -X POST "$ES_HOST:$ES_PORT/$ES_INDEX_NAME/$ES_MAPPING_NAME/" -H 'Content-Type: application/json' -d'
{
	"type": "'$ES_TYPE_NAME'",
    "content": "The More Like This Query finds documents that are like a given set of documents.",
    "external_project_id" : 1,
    "external_doc_id" : 2,
    "external_page_id" : 120
}
'
