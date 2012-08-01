#!/bin/bash

RELEASE=$1

SCRIPTS_DIR=$(dirname $0)
GLADIATOR_JAR=$SCRIPTS_DIR/../releases/$RELEASE

java -Xmx2048M -jar "$GLADIATOR_JAR" 
