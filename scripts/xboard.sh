#!/bin/bash

SCRIPTS_DIR=$(dirname $0)
GLADIATOR_SCRIPT=$SCRIPTS_DIR/gladiator.sh

xboard -tc 2:30 -inc 5 -size 64 -mg 20 -fcp "$GLADIATOR_SCRIPT"
