package reg;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Storage provider for a MongoDB.
 */
class StorageProviderMongoDB {

	private static final String MONGO_URL = "mongodb://141.19.142.59:27017";
	/** URI to the MongoDB instance. */
	private static MongoClientURI connectionString = new MongoClientURI(MONGO_URL);

	/** Client to be used. */
	private static MongoClient mongoClient = new MongoClient(connectionString);

	/** Mongo database. */
	private static MongoDatabase database = mongoClient.getDatabase("userbase");

	/**
	 * @see var.chat.server.persistence.StorageProvider#retrieveMessages(java.lang.String,
	 *      long, boolean)
	 */

    public synchronized void storePassword(String userData) {
        MongoCollection<Document> collection = database.getCollection("account");

        String [] string = userData.split("_");
        Document doc = new Document("storedPassword", string[0])
                .append("user", string[1]);

        collection.insertOne(doc);
    }


    /**
     *
     * @param token
     * @param pseudonym
     * @return
     */
    public synchronized String retrieveToken(String token,String pseudonym){
    	MongoCollection<Document> collection = database.getCollection("token");
    	Document doc = collection.find(and(eq("pseudonym", pseudonym), eq("token", token))).first();
		if (doc == null) {
			return null;
		}
		return doc.getString("token")+":"+doc.getString("pseudonym");
    }

    /**
     *
     * @param password
     */
    public synchronized void deletePassword(String password){
    	MongoCollection<Document> collection = database.getCollection("account");
    	collection.deleteOne(eq("password",password));
    }

    /**
     * @see var.chat.server.persistence.StorageProvider#clearForTest()
     */
    public void clearForTest() {
        database.getCollection("password").drop();
    }
}
