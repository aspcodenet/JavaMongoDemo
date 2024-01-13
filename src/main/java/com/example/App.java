package com.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
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
      String connectionString = "mongodb+srv://stefanholmberg:hejsan123@cluster0.omzqfhx.mongodb.net/?retryWrites=true&w=majority";
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .build();



        MongoDatabase db;
        try (MongoClient mongoClient = MongoClients.create(settings)){
        //try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {
            db = mongoClient.getDatabase("Whatever2");
            MongoCollection<Document> coll = db.getCollection("person");

            Document doc = new Document()
                    .append("_id", "20080528-0000")
                    .append("namn", "Oliver")
                    .append("city", "Nacka")
                    .append("age", 15);
            coll.insertOne(doc);


            doc = new Document()
                    .append("_id", "19720803-0000")
                    .append("namn", "Stefan")
                    .append("city", "Nacka")
                    .append("age", 51)
                    .append("coolFactor", false);
            coll.insertOne(doc);


            long l = coll.countDocuments();
            System.out.println(l);

            //list all
            for(Document d : coll.find()){
                System.out.println(d.getString("namn"));
            }

            //Update - lets take the first one
            String key = (String) coll.find().first().get("_id");
            coll.updateOne(new Document("_id", key),
                    new Document("$set", new Document("namn", "Test"))
                    );


        }


    System.out.println( "Hello World!");
    }
}
