package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.*;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class ElasticsearchBithubRepository {

    @Autowired
    private RestHighLevelClient client;

    public CreateIndexResponse createUserIndex(User user/*String indexName nombre del modelo en minuscula*/){
        CreateIndexRequest request = new CreateIndexRequest("users");
        Map<String, Object> userEmail = new HashMap<>();
        userEmail.put("type", "string");
        Map<String, Object> userName = new HashMap<>();
        userName.put("type", "string");
        Map<String, Object> properties = new HashMap<>();
        properties.put("email", userEmail);
        properties.put("name", userName);
        Map<String, Object> mapping = new HashMap<>();
        mapping.put("properties", properties);
        request.mapping(mapping);

        request.mapping(mapping);
        client.indices().create(request);
    }

}
