package ar.edu.unlp.info.bd2.config;

/*import org.elasticsearch.elasticsearch;
import org.elasticsearch.client.elasticsearch-rest-client;
*/

import ar.edu.unlp.info.bd2.repositories.ElasticsearchBithubRepository;
import ar.edu.unlp.info.bd2.services.BithubService;
import ar.edu.unlp.info.bd2.services.ElasticsearchService;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticConfiguration {

    @Bean
    public static synchronized RestHighLevelClient makeConnection() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")/*,
                        new HttpHost("localhost", 9201, "http")*/));
        return client;
    }

    @Bean
    public BithubService elasticService(){
        ElasticsearchBithubRepository repo = this.createElasticRepostiry();
        return new ElasticsearchService(repo);
    }

    @Bean
    public ElasticsearchBithubRepository createElasticRepostiry(){
        return new ElasticsearchBithubRepository();
    }
}
