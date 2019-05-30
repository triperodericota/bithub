package ar.edu.unlp.info.bd2.repositories;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.*;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.mongo.Association;
import com.mongodb.client.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

public class MongoDBBithubRepository {

  @Autowired
  private MongoClient client;

  @Autowired
  private MongoDatabase database;

  public void saveBranch(Branch newBranch){
      MongoCollection collection= database.getCollection("branch");
      BasicDBObject document= new BasicDBObject();
      document.put("name",newBranch.getName());
      collection.insertOne(document);
  }

}
