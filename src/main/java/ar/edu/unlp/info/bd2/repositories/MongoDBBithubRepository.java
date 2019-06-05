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

  public MongoDatabase getDB(){
        return client.getDatabase("bd2");
  }

  public void saveBranch(Branch newBranch){
      MongoCollection<Branch> collection = this.getDB().getCollection("branch",Branch.class);
      collection.insertOne(newBranch);
  }

  public void saveUser(User newUser){
      MongoCollection<User> collection = this.getDB().getCollection("user",User.class);
      collection.insertOne(newUser);
  }

  public void saveFile(File newFile){
      MongoCollection<File> collection = this.getDB().getCollection("file", File.class);
      collection.insertOne(newFile);
  }

  public void saveCommit(Commit newCommit){
      MongoCollection<Commit> collection = this.getDB().getCollection("commit",Commit.class);
      collection.insertOne(newCommit);
  }

  public Optional<Commit> getCommitByHash(String commitHash){
      MongoCollection<Commit> collection = this.getDB().getCollection("commit", Commit.class);
      return Optional.ofNullable(collection.find(eq("hash",commitHash)).first());
  }

  public Optional<Branch> getBranchByName(String branchName){
      MongoCollection<Branch> collection = this.getDB().getCollection("branch", Branch.class);
      return Optional.ofNullable(collection.find(eq("name", branchName)).first());
  }

}
