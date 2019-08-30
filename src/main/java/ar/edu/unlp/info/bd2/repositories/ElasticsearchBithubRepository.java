package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ElasticsearchBithubRepository {

    private RestHighLevelClient client;
    private ObjectMapper objectMapper;

    @Autowired
    public ElasticsearchBithubRepository(RestHighLevelClient cli){
        client = cli;
        objectMapper = new ObjectMapper();
    }

    public CreateIndexResponse createUserIndex() throws IOException {
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
        CreateIndexResponse indexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println("--- indice creado: " + indexResponse.isAcknowledged());
        return indexResponse;
    }

    public String createUser(User user) throws IOException {
        GetIndexRequest request = new GetIndexRequest("users");
        if(!client.indices().exists(request, RequestOptions.DEFAULT)) { this.createUserIndex(); }
        String id = UUID.randomUUID().toString().replaceAll("[^\\d]", "");
        /*user.setId(Long.parseLong(id));*/
        user.setId(Long.getLong(id));

        Map<String, Object> documentMapper = objectMapper.convertValue(user, Map.class);

        IndexRequest indexRequest = new IndexRequest("users");
        indexRequest.id(id);
        indexRequest.source(documentMapper);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        return indexResponse.getResult().name();
    }

}
