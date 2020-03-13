##### README

BitHub is Java multi-layer app using Maven and Sping oriented to study persistence's concepts.
Storage a common domain model in differents ORDBMS (MySQL, MongoDB) using Hibernate, Mongo, Sping Data and ElastisSearch 

The buisenss model is represented in objects oriented paradigm in _model_ folder. In _repositories_ folder you can view
the classes that represents the data access layer for each persistence system and the necessary behaviour (object's methods) to satisfied the test
case suite. Each persistence alternative has its service (which comunicate with the correspondent repository) and test cases to check if data is correctly storage.
Each service and test case implements a common interface. Also each alternative has its configuration in _config_ folder.

**Dependencies:**

· Java 8

· Maven 3.5.0

· MySQL 5.7

· MongoDB 4.0

· ELK stack (https://www.elastic.co/es/products/elastic-stack)

**Set up**

1 - Clone repository

2 - Is necessary have all database manager systems on active state and running correctly on config's spicified ports 

3 - With _mvn clean install_ in repository directory it's possible run tests and obtain a successful build


**Migrate data to ElasticSearch**

1 - Run Elastic server (in Linux) with:

    · /etc/init.d/elasticsearch start

	· check connection via browser on https://localhost:9200

2 - Run Kibana server with:

	· cd  /path/to/../kibana-7.3.1-linux-x86_64

	· bin/kibana

	· access via browser to https://localhost:5601

Run elasticApp/ElasticApp.jar file, which create necessary indexs to load seed data over domain model. To visualizate this in Kibana access to the "Managment" section
(http://localhost:5601/app/kibana#/management/elasticsearch/index_management/indices?_g=()). In http://localhost:5601/app/kibana#/management/kibana/index_patterns?_g=()
it's possible create new index patterns to generate future visualizations.


**Support to Mongo DB using Logstash**

Using Logstash, I create pipeline that read data from Mongo DB and write this on ElasticSearch,
using availables plugins. 

Run with:

1 - cd /path/to/../logstash-7.3.1

2 - Install necessary plugins:

	· bin/logstash-plugin install logstash-output-elasticsearch

	· bin/logstash-plugin install logstash-input-jdbc
	
3 - Configure  jdbc-mongo-input.conf file. "library" attribute must be absolute path to driver's library, 
which is located in UnityJDBC


Problems

Por cuestiones de tiempo no pude llegar a chequear porque no se migran los datos correctamente. Necesidad de debuggear si el error está en el statement del
input o donde. Posibilidad de aplicar un filtro.
