#!/bin/bash

# Set environment variables
echo "1. Setting environment variables"
source 00_setEnv.sh

# Export path
export PATH=$PATH:$usr_glassfish_bin_path

# Authentication topic
echo "2. Delete JMS resource"
asadmin --interactive=false --echo delete-jms-resource jms/$JMS_TOPIC_AUTH
