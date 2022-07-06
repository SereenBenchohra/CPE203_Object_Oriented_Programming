#!/bin/sh -x

javac *.java
if [ $? != 0 ] ; then
    exit 1
fi
java -ea Calculator
rm *.class
