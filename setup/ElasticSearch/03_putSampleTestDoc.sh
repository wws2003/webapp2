#!/bin/bash
# Set environment variables for test
source 00_setEnv_forTest.sh

echo "To create document in index @$ES_HOST:$ES_PORT/$ES_INDEX_NAME"

# The simplest case: Single index for the whole application
# Document id = 1
curl -X PUT "$ES_HOST:$ES_PORT/$ES_INDEX_NAME/_doc/1/_create" -H 'Content-Type: application/json' -d'
{
    "content": "The More Like This Query finds documents that are like a given set of documents.",
    "external_project_id" : 1,
    "external_doc_id" : 2,
    "external_page_id" : 120
}
'
