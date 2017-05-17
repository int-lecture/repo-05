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
	private static MongoDatabase database = mongoClient.getDatabase("users");

	/**
	 * @see var.chat.server.persistence.StorageProvider#retrieveMessages(java.lang.String,
	 *      long, boolean)
	 */

    public static synchronized void storePassword(String userData) {
        MongoCollection<Document> collection = database.getCollection("account");

        String [] string = userData.split(":");
        Document doc = new Document("salt", string[1])
                .append("password", string[2])
                .append("username", string[3]);
        collection.insertOne(doc);
    }

    /**
     *
     * @param token
     * @param pseudonym
     * @return
     */
    public static synchronized String retrieveToken(String token,String pseudonym){
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
    public static synchronized void deletePassword(String password){
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
