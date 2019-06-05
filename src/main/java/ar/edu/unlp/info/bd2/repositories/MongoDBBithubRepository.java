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

  public void saveBranch(Branch newBranch){
      MongoCollection<Branch> collection = this.retrieveCollection("Branch");
      collection.insertOne(newBranch);
  }

  public void saveUser(User newUser){
      MongoCollection<User> collection = this.retrieveCollection("User");
      collection.insertOne(newUser);
  }

  public void saveFile(File newFile){
      MongoCollection<File> collection = this.retrieveCollection("File");
      collection.insertOne(newFile);
  }

  public void saveCommit(Commit newCommit){
      MongoCollection<Commit> collection = this.retrieveCollection("Commit");
      collection.insertOne(newCommit);
  }

  public Optional<Commit> getCommitByHash(String commitHash){
      MongoCollection<Commit> collection = this.retrieveCollection("Commit");
      Commit commit = collection.find(eq("hash",commitHash)).first();
      return Optional.of(commit);
  }



}
