#!/bin/bash
# Detect postgres patha based on OS
if [ "$(uname)" == "Darwin" ]; then
    # Do something under Mac OS X platform
	export usr_glassfish_home_prefix=$HOME/servers
elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
    # Do something under GNU/Linux platform
	export usr_glassfish_home_prefix=$HOME/servers
elif [ "$(expr substr $(uname -s) 1 10)" == "MINGW32_NT" ]; then
    # Do something under 32 bits Windows NT platform
	export usr_glassfish_home_prefix=$HOME/servers
elif [ "$(expr substr $(uname -s) 1 10)" == "MINGW64_NT" ]; then
    # Do something under 64 bits Windows NT platform
	export usr_glassfish_home_prefix=$HOME/servers/glassfish-4.1.2/
fi

# Glassfish bin folder
export usr_glassfish_bin_path=$usr_glassfish_home_prefix/glassfish4/bin

export JMS_TOPIC_AUTH=mendelAuthTopic
export JMS_TOPIC_DOCUMENT_INDEX=mendelDocumentIndexTopic
export JMS_TOPIC_SEARCH=mendelSearchQTopic
