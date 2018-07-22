#!/bin/bash
if [ $# -gt 0 ] 
then
    if [ $1 -eq 1 ]
    then
	echo '123'
    fi
fi
# Check artifact class (to see if web-common, lib-common classes included)
echo ----------Check artifact class to see if web-common, lib-common classes included----------
ls -la web-all/target/mendel-all-1.0/WEB-INF/classes/org/hpg
ls -la web-all/target/mendel-all-1.0/WEB-INF/lib/lib-common*.jar

# Check artifact resources (to see if web-common webapp included)
echo ---------Check artifact resources to see if web-common webapp included------------
ls -la web-all/target/mendel-all-1.0/WEB-INF/views

