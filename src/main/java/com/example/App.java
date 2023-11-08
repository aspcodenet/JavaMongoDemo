package com.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.BsonDocument;
import org.bson.Document;

import java.util.UUID;
import java.util.logging.Level;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ){
//        java.util.logging.Logger.getLogger("JULLogger").setLevel(Level.OFF);
        MongoDatabase db;
        try (MongoClient mongoClient = MongoClients.create()){
        //try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {
            db = mongoClient.getDatabase("Whatever");
            MongoCollection<Document> coll = db.getCollection("customers");

            Document doc = new Document()
                    .append("_id", UUID.randomUUID())
                    .append("namn", "Stefan").append("city", "Nacka");
            coll.insertOne(doc);

            long l = coll.countDocuments();
            System.out.println(l);

            //list all
            for(Document d : coll.find()){
                System.out.println(d.getString("namn"));
            }

            //Update - lets take the first one
            UUID key = (UUID) coll.find().first().get("_id");
            coll.updateOne(new Document("_id", key),
                    new Document("$set", new Document("namn", "Test"))
                    );


        }


    System.out.println( "Hello World!");
    }
}
