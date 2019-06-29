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

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
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

    private MongoCollection retrieveAssociationCollection(String associationName){
        return this.getDB().getCollection(associationName, Association.class);
    }

    public void saveDocument(Object obj, String className){
        MongoCollection collection = this.retrieveCollection(className);
        collection.insertOne(obj);
    }

    public void saveAssociation(String associationName, Association association){
        MongoCollection collection = this.retrieveAssociationCollection(associationName);
        collection.insertOne(association);
    }
    public FindIterable getDocument(String field,Object parameter,String modelName){
        MongoCollection collection = this.retrieveCollection(modelName);
        return collection.find(eq(field,parameter));
    }

  /*public void newDocument(PersistentObject olderDocument, String className){
      MongoCollection collection = this.retrieveCollection(className);
      if(olderDocument.getId()!=null){
          collection.replaceOne(eq("objectId", olderDocument.getId()), olderDocument, new ReplaceOptions().upsert(false));
      }else{
          collection.insertOne(olderDocument);
      }*/

    public FindIterable<Association> getAssociations(String field,Object parameter, String associationName){
        MongoCollection collection = this.retrieveAssociationCollection(associationName);
        return collection.find(eq(field,parameter));
    }

    public void replaceDocument(PersistentObject updatedDocument, String className){
        MongoCollection collection = this.retrieveCollection(className);
        collection.replaceOne(eq("_id", updatedDocument.getObjectId()), updatedDocument);
    }

    public FindIterable findCommitsForUser(ObjectId userId){
        MongoCollection collection = this.retrieveAssociationCollection("commit_author");
        collection.aggregate(Arrays.asList(
                Aggregates.match(Filters.eq("destination", userId)),
                /*Aggregates.lookup("commit", "source", "_id", "commit_data"),*/
                Aggregates.project(Projections.fields(Projections.include("source"),Projections.excludeId())),
                Aggregates.out("commitsForUser"))).toCollection();
        return this.getDB().getCollection("commitsForUser").find();
    }

    public FindIterable computedTotalCommitsPerUser(){
        MongoCollection collection = this.retrieveAssociationCollection("commit_author");
        collection.aggregate(Arrays.asList(
                Aggregates.group("$destination", Accumulators.sum("totalCommits",1)),
                Aggregates.project(Projections.fields(Projections.include("destination","totalCommits"))),
                Aggregates.out("commitsPerUser"))).toCollection();
        return this.getDB().getCollection("commitsPerUser").find();
    }

    public FindIterable usersThatCommitedInBranch(String branchName){
        MongoCollection collection = this.retrieveCollection("Branch");
        collection.aggregate(Arrays.asList(
                Aggregates.match(Filters.eq("name", branchName)),
                Aggregates.unwind("$commits"),
                Aggregates.group("$commits.author"),
                Aggregates.project(Projections.fields(Projections.include("_id._id"))),
                Aggregates.out("usersInBranch"))).toCollection();
        return this.getDB().getCollection("usersInBranch").find();
    }


}