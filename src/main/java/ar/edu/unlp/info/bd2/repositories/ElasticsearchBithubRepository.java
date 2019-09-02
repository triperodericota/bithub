package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class ElasticsearchBithubRepository {

    private RestHighLevelClient client;
    private ObjectMapper objectMapper;

    @Autowired
    public ElasticsearchBithubRepository(RestHighLevelClient cli){
        client = cli;
        objectMapper = new ObjectMapper();
    }

    private CreateIndexResponse createNewIndex(Object document, String indexName) throws IOException{
        CreateIndexRequest request = new CreateIndexRequest(indexName);

        Map<String, Map> properties = new HashMap<>();
        Map<String, Map> mapping = new HashMap<>();

        Field[] fields = document.getClass().getFields();
        Arrays.stream(fields).forEach(field -> {
            String fieldName = field.getName();
            String fieldClass = field.getClass().getSimpleName();
            Map<String, String> fieldMap = new HashMap<>();
            fieldMap.put("type", fieldClass);
            properties.put(fieldName, fieldMap);
        });
        mapping.put("properties", properties);
        request.mapping(mapping);
        System.out.println(mapping.toString());

        CreateIndexResponse indexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println("--- indice creado: " + indexResponse.isAcknowledged());
        return indexResponse;
    }

    public String createDocument(PersistentObject doc, String indexName) throws IOException{
        GetIndexRequest request = new GetIndexRequest(indexName);
        if(!client.indices().exists(request, RequestOptions.DEFAULT)) { this.createNewIndex(doc, indexName); }
        String id = UUID.randomUUID().toString().replaceAll("[^\\d]", "");
        /*user.setId(Long.parseLong(id));*/
        doc.setId(Long.getLong(id));

        Map<String, Object> documentMapper = objectMapper.convertValue(doc, Map.class);

        IndexRequest indexRequest = new IndexRequest(indexName);
        indexRequest.id(id);
        indexRequest.source(documentMapper);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        return indexResponse.getResult().name();
    }

/*
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

        CreateIndexResponse indexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println("--- indice creado: " + indexResponse.isAcknowledged());
        return indexResponse;
    }

    public String createUser(User user) throws IOException {
        GetIndexRequest request = new GetIndexRequest("users");
        if(!client.indices().exists(request, RequestOptions.DEFAULT)) { this.createUserIndex(); }
        String id = UUID.randomUUID().toString().replaceAll("[^\\d]", "");
        /*user.setId(Long.parseLong(id));
        user.setId(Long.getLong(id));

        Map<String, Object> documentMapper = objectMapper.convertValue(user, Map.class);

        IndexRequest indexRequest = new IndexRequest("users");
        indexRequest.id(id);
        indexRequest.source(documentMapper);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        return indexResponse.getResult().name();
    }
*/

    private SearchResponse searchMatchQuery(String indexName, String fieldName, Object value) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(fieldName, value);
        searchSourceBuilder.query(matchQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("--- Query status: " + searchResponse.status());
        return searchResponse;
    }

    public Optional<Commit> findCommitByHash(String commitHash) throws IOException {
        SearchResponse searchResponse = null;
        try {
            searchResponse = this.searchMatchQuery("commits", "hash", commitHash);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* access to the returned document */
        SearchHits hits = searchResponse.getHits();
        Commit toReturn = objectMapper.readValue((JsonParser) hits.getAt(0).getSourceAsMap(), Commit.class);
        System.out.println("Commit with hash " + commitHash +": " + toReturn.getMessage());
        return Optional.of(toReturn);
    }

}
