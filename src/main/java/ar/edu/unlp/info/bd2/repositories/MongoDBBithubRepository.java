package ar.edu.unlp.info.bd2.repositories;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;


import ar.edu.unlp.info.bd2.model.Tag;

import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
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

    public Optional getDocument(String field,Object parameter,String modelName){
        MongoCollection collection = this.retrieveCollection(modelName);
        return Optional.ofNullable(collection.find(eq(field,parameter)).first());
    }

    public FindIterable getDocuments(String field,Object parameter,String modelName){
        MongoCollection collection = this.retrieveCollection(modelName);
        return collection.find(eq(field,parameter));
    }

    public FindIterable<Association> getAssociations(String field,Object parameter, String associationName){
        MongoCollection collection = this.retrieveAssociationCollection(associationName);
        return collection.find(eq(field,parameter));
    }

    public void replaceDocument(PersistentObject updatedDocument, String className){
        MongoCollection collection = this.retrieveCollection(className);
        collection.replaceOne(eq("_id", updatedDocument.getObjectId()), updatedDocument);
    }

    public AggregateIterable findCommitsForUser(ObjectId userId){
        MongoCollection collection = this.retrieveAssociationCollection("commit_author");
        return collection.aggregate(Arrays.asList(
                Aggregates.match(Filters.eq("destination", userId)),
                Aggregates.project(fields(include("source"),Projections.excludeId()))));
    }

    public Iterator<Document> computedTotalCommitsPerUser(){
        MongoCollection collection = this.retrieveAssociationCollection("commit_author");
        return collection.aggregate(Arrays.asList(
                new Document("$group", new Document("_id", "$destination").append("count", new Document("$sum", 1)))
        ), Document.class).iterator();
    }

    public List<User> usersThatCommitedInBranch(String branchName){
        MongoCollection collection = this.retrieveCollection("Branch");
        AggregateIterable<User> result = collection.aggregate(Arrays.asList(
                match(eq("name", branchName)),
                lookup("commit", "commits._id", "_id", "usersInBranch"),
                lookup("commit_author","commits._id", "source", "usersInBranch"),
                lookup("user","usersInBranch.destination", "_id", "usersInBranch"),
                unwind("$usersInBranch"),
                replaceRoot("$usersInBranch")), User.class);

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(result.iterator(),0),false).collect(Collectors.toList());

    }
}