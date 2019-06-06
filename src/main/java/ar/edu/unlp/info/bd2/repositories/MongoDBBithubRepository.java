package ar.edu.unlp.info.bd2.repositories;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Updates.push;

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

  private MongoDatabase getDB(){
        return client.getDatabase("bd2");
  }

  private MongoCollection retrieveCollection(String className){
      String fullyClassName = "ar.edu.unlp.info.bd2.model." + className;
      Class<?> cls = null;
      try {
          cls = Class.forName(fullyClassName);
      } catch (ClassNotFoundException e){
          e.printStackTrace();
      }

      return this.getDB().getCollection(className.toLowerCase(), cls);
  }

  public void saveDocument(Object obj, String className){
    MongoCollection collection = this.retrieveCollection(className);
    collection.insertOne(obj);
  }
      //MongoCollection<Branch> collectionBranchs = this.retrieveCollection("Branch");
      //collectionBranchs.updateOne(eq("name",newCommit.getBranch().getName()),push("commits",newCommit));

  public Optional getDocument(String field,Object parameter,String className){
      MongoCollection collection = this.retrieveCollection(className);
      return Optional.ofNullable(collection.find(eq(field,parameter)).first());
  }
}
