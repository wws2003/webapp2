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

echo "To create index @$ES_HOST:$ES_PORT/$ES_INDEX_NAME"

# The simplest case: Single index for the whole application
curl -X PUT "$ES_HOST:$ES_PORT/$ES_INDEX_NAME" -H 'Content-Type: application/json' -d'
{
    "settings" : {
        "number_of_shards" : 1
    },
    "mappings" : {
        "$ES_MAPPING_NAME" : {
            "properties" : {
				"type": {"type": "keyword"},
                "content" : { "type" : "text" },
				"external_project_id" : { "type" : "long" },
				"external_doc_id" : { "type" : "long" },
				"external_page_id" : { "type" : "long" }
            }
        }
    }
}
'
