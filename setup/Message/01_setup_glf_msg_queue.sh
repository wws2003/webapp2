#!/bin/bash

# Set environment variables
echo "1. Setting environment variables"
source 00_setEnv.sh

# Export path
export PATH=$PATH:$usr_glassfish_bin_path

# Authentication topic
echo "2. Create JMS resource"
asadmin --interactive=false --echo create-jms-resource --restype javax.jms.Topic --property Name=$JMS_TOPIC_AUTH jms/$JMS_TOPIC_AUTH
