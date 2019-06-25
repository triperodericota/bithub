package ar.edu.unlp.info.bd2.repositories;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;


import ar.edu.unlp.info.bd2.model.Tag;

import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Updates.push;

import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.ReplaceOptions.*;

import com.mongodb.*;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.mongo.Association;
import com.mongodb.client.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.mongodb.client.model.UpdateOptions;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

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

  public void saveAssociation(String associationName, Association association){
      MongoCollection collection = this.getDB().getCollection(associationName, Association.class);
      collection.insertOne(association);

  }

  public Optional getDocument(String field,Object parameter,String className){
      MongoCollection collection = this.retrieveCollection(className);
      return Optional.ofNullable(collection.find(eq(field,parameter)).first());
  }

  /*public void newDocument(PersistentObject olderDocument, String className){
      MongoCollection collection = this.retrieveCollection(className);
      if(olderDocument.getId()!=null){
          collection.replaceOne(eq("objectId", olderDocument.getId()), olderDocument, new ReplaceOptions().upsert(false));
      }else{
          collection.insertOne(olderDocument);
      }*/

  public void replaceDocument(PersistentObject updatedDocument, String className){
     /* Optional<Branch> b = this.getDocument("objectId", updatedDocument.getId(), "Branch");*/
      System.out.println("--- updatedDocument ==> " + updatedDocument.getClass().toString() + updatedDocument.getId() );
      MongoCollection collection = this.retrieveCollection(className);
      collection.replaceOne(eq("objectId", updatedDocument.getId()), updatedDocument);
      System.out.println("--- updatedDocument ==> " + updatedDocument.getClass().toString() + updatedDocument.getId() );
  }

}
