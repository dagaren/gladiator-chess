#!/bin/bash

SCRIPTS_DIR=$(dirname $0)
GLADIATOR_JAR=$SCRIPTS_DIR/../app/Gladiator/bin/Gladiator.jar

java -Xmx2048M -jar "$GLADIATOR_JAR" 
