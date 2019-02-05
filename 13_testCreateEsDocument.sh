#/bin/!bash
export WORKING_DIR=${PWD}
cd $WORKING_DIR/web-project
mvn -Dtest=org.hpg.project.dao.repository.es.IEsDocumentRepositoryTest#testPutSampleDocument surefire:test
cd $WORKING_DIR/setup/ElasticSearch
source 00_setEnv.sh
curl -X GET "$ES_HOST:$ES_PORT/$ES_INDEX_NAME/_search?pretty"

