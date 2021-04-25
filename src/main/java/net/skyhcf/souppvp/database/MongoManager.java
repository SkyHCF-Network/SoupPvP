package net.skyhcf.souppvp.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;

public class MongoManager {

    @Getter private MongoClient mongoClient;
    @Getter private MongoDatabase database;
    @Getter private MongoCollection<Document> profiles;

    public void connect(){
        this.mongoClient = MongoClients.create("mongodb://localhost");
        try {
            this.database = mongoClient.getDatabase("SoupPvP");
            this.profiles = database.getCollection("profiles");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
