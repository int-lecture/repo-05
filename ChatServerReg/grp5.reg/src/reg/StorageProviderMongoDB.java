package reg;


import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lte;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


/**
 * Storage provider for a MongoDB.
 */
class StorageProviderMongoDB {

    /** URI to the MongoDB instance. */
    private static MongoClientURI connectionString =
            new MongoClientURI("mongo_db://141.19.142.59/");

    /** Client to be used. */
    private static MongoClient mongoClient = new MongoClient(connectionString);

    /** Mongo database. */
    private static MongoDatabase database = mongoClient.getDatabase("login");

    /**
     * @see var.chat.server.persistence.StorageProvider#storeMessage(var.chat.server.domain.Message)
     */
    public static synchronized void storePassword(String userData) {
        MongoCollection<Document> collection = database.getCollection("userdata");

        String [] string = userData.split(":");
        Document doc = new Document("iterations", string[0])
                .append("salt", string[1])
                .append("password", string[2])
                .append("user", string[3]);
        collection.insertOne(doc);
    }

    /**
     * @see var.chat.server.persistence.StorageProvider#clearForTest()
     */
    public void clearForTest() {
        database.getCollection("password").drop();
    }
}
