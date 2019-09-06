Informe de las actividades realizadas

Como extensión del trabajo realizado durante la cursada, opté por continuar la arquitectura en capas ya desarrolladas para brindar soporte de almacenamiento sobre
ElasticSearch. 

Requerimientos:

- Instalacion del stack ELK (https://www.elastic.co/es/products/elastic-stack)

Migración de los datos hacia ElasticSearch


A partir de la creción de la configuración pertinente para establecer la conexión con el servicio(config/ElasticConfiguration.java) y el diseño del servicio
y el repositorio necesario para continuar respetando la arquitectura en capas ya dispuesta, se creo un main (elasticApp/ElasticApp.java) como start point.


Ejecucion y visualización de los datos en Kibana


1 - Levantar el servidor elastic

	· (en linux: /etc/init.d/elasticsearch start)
	
	· comprobar la conexión accecidiendo via curl o via browser a https://localhost:9200
	
2 - Levantar el servidor kibana

	· cd  /path/../kibana-7.3.1-linux-x86_64
	
	· bin/kibana
	
	· acceso via browser a https://localhost:5601

Ejecutar el .jar disponible en elasticApp/ElasticApp.jar el cual va a crear los índices necesarios para cargar datos de ejemplos sobre el dominio de negocio
manejado durante la cursada. Para la visualización de los índices en kibana acceder a la sección de Managment (http://localhost:5601/app/kibana#/management/elasticsearch/index_management/indices?_g=())
en donde se podrán ver los índices creados y los datos cargados. en http://localhost:5601/app/kibana#/management/kibana/index_patterns?_g=() se podrán crear nuevos index patterns
para poder generar posteriores visualizaciones.

Tambien es posible visualizar los índices en: https://localhost:9200/_aliases?pretty=true y los documentos de un índice en: https://localhost:9200/{indexName}/_search?


Soporte de la base en Mongo usando Logstash

Con Logstash se intentó crear un pipeline que "tome" los datos desde la base de mongo y los vuelque en ElasticSearch, utilizando los plugins disponibles

Para su ejecución:

1 - cd /path/../logstash-7.3.1

2 - Instalación de plugins necesarios:

	· bin/logstash-plugin install logstash-output-elasticsearch
	
	· bin/logstash-plugin install logstash-input-jdbc
	
3 - Configurar el archivo jdbc-mongo-input.conf .en el atributo library debe tener como valor el path absoluto a la libreria del driver, el cual se encuentra en
la carpeta UnityJDBC.


Problemas

Por cuestiones de tiempo no pude llegar a chequear porque no se migran los datos correctamente. Necesidad de debuggear si el error está en el statement del
input o donde. Posibilidad de aplicar un filtro.
