#!/bin/bash

SCRIPT_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && pwd)
echo $SCRIPT_DIR;

git clean -fX $SCRIPT_DIR/temp/
git clean -fX $SCRIPT_DIR/files/
