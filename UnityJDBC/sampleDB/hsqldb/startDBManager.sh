#!/bin/bash
DIR=$( cd "$( dirname "$0" )" && pwd )
cd $DIR
java -cp hsqldb.jar org.hsqldb.util.DatabaseManager