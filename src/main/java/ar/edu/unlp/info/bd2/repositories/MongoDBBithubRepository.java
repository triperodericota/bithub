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


  public void saveBranch(Branch newBranch){
      MongoCollection<Branch> collection = client.getDatabase("bd2").getCollection("branch",Branch.class);
      collection.insertOne(newBranch);
  }

  public void saveUser(User newUser){
      MongoCollection<User> collection = client.getDatabase("bd2").getCollection("user",User.class);
      collection.insertOne(newUser);
  }

}
