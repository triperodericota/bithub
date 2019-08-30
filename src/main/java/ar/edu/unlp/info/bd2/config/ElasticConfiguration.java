package ar.edu.unlp.info.bd2.config;

/*import org.elasticsearch.elasticsearch;
import org.elasticsearch.client.elasticsearch-rest-client;
*/

import ar.edu.unlp.info.bd2.repositories.ElasticsearchBithubRepository;
import ar.edu.unlp.info.bd2.services.BithubService;
import ar.edu.unlp.info.bd2.services.ElasticsearchService;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.MainResponse;
import org.elasticsearch.cluster.ClusterName;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.logging.Logger;

@Configuration
public class ElasticConfiguration {

    @Bean
    public static synchronized RestHighLevelClient makeConnection() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")/*,
                        new HttpHost("localhost", 9201, "http")*/));
        Logger logger = Logger.getGlobal();
        logger.info("Cliente conectado. ");

        MainResponse response = null;
        try {
            response = client.info(RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String clusterName = response.getClusterName();
        String clusterUuid = response.getClusterUuid();
        String nodeName = response.getNodeName();
        MainResponse.Version version = response.getVersion();

        logger.info("Información del cluster: ");

        logger.info("Nombre del cluster: {}" + clusterName);
        logger.info("Identificador del cluster: {}" + clusterUuid);
        logger.info("Nombre de los nodos del cluster: {}" + nodeName);
        logger.info("Versión de elasticsearch del cluster: {}" + version.toString());

        return client;
    }

    @Bean
    public BithubService elasticService(){
        ElasticsearchBithubRepository repo = this.createElasticRepostiry();
        return new ElasticsearchService(repo);
    }

    @Bean
    public ElasticsearchBithubRepository createElasticRepostiry(){
        RestHighLevelClient client = ElasticConfiguration.makeConnection();
        return new ElasticsearchBithubRepository(client);
    }




}
