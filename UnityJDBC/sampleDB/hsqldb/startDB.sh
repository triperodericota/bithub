#!/bin/bash
DIR=$( cd "$( dirname "$0" )" && pwd )
cd $DIR
java -Xmx300M -Xms300M -cp hsqldb.jar org.hsqldb.Server -database.0 file:mydb -dbname.0 xdb -database.1 file:emptydb -dbname.1 emptydb -database.2 file:tpch -dbname.2 tpch
