#!/bin/bash
SCRIPT_DIR=`dirname $BASH_SOURCE`;
SCRIPT_DIR=`readlink -f $SCRIPT_DIR`;
WORKING_DIR=`pwd`;

source "$SCRIPT_DIR/version.sh";
source "$SCRIPT_DIR/config.sh";

usage()
{
cat << EOF

---------------------------------------------------------------------------------
  usage: $0
      [google account]
      [password]
      [other arguments]

  This script setups the GAE application
---------------------------------------------------------------------------------

EOF
}

if [ "$1" == "" ]; then
    usage;
    exit 1;
fi

if [ "$2" == "" ]; then
    usage;
    exit 1;
fi

if [ -d "$SCRIPT_DIR/richfaces-gae" ]; then
    rm -rf "$SCRIPT_DIR/richfaces-gae";
fi

cd $SCRIPT_DIR;
${MAVEN} ${MAVEN_ARGS} archetype:generate -Dbasedir=${SCRIPT_DIR} -DarchetypeGroupId=org.richfaces.archetypes -DarchetypeArtifactId=richfaces-archetype-gae -DarchetypeVersion=${RICHFACES_VERSION} -DgroupId=org.richfaces.tests.archetypes -DartifactId=richfaces-gae -Dversion=${RICHFACES_VERSION} -Dpackage=org.richfaces.tests.archetypes.gae -DinteractiveMode=false ${@:3};
${MAVEN} ${MAVEN_ARGS} -f $SCRIPT_DIR/richfaces-gae/pom.xml clean package;
cd $WORKING_DIR;

if [ ! -d "$SCRIPT_DIR/appengine-java-sdk" ]; then
    wget http://googleappengine.googlecode.com/files/appengine-java-sdk-1.6.1.zip -O $SCRIPT_DIR/appengine-java-sdk.zip;
    unzip $SCRIPT_DIR/appengine-java-sdk.zip -d $SCRIPT_DIR;
    rm -rf $SCRIPT_DIR/appengine-java-sdk.zip;
    mv $SCRIPT_DIR/appengine-java-sdk-1.6.1 $SCRIPT_DIR/appengine-java-sdk; 
fi

echo "$2" | $SCRIPT_DIR/appengine-java-sdk/bin/appcfg.sh  -e $1 update $SCRIPT_DIR/richfaces-gae/target/richfaces-gae;
