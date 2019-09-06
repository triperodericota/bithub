@ECHO OFF
REM This command below gets the current directory of the script file and changes to that directory.
cd /d %~dp0
java -cp .;lib/*;unityjdbc.jar;drivers/HSQL/2_2/hsqldb.jar;drivers/DB2/db2jcc.jar;drivers/Informix/ifxjdbc.jar;drivers/Sybase/jconn4.jar;drivers/MySQL/mysql.jar;drivers/Oracle11g/oracle.jar;drivers/PostgreSQL/postgresql.jar;drivers/SQLServer/sqljdbc4.jar;drivers/Mongo/mongo-java-driver-3.0.3.jar;drivers/Splunk/splunk-sdk-java-1.5.0.jar;drivers/Splunk/gson-2.2.4.jar com.unityjdbc.sourcebuilder.SourceBuilder
