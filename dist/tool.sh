#!/bin/bash

export CLASSPATH=.
for jar in ./lib/*.jar; do
    CLASSPATH=$CLASSPATH:$jar
done

java -classpath $CLASSPATH com.linkstec.raptor.eagle.tool.Main $1
