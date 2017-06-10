package server;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Filters.gt;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.bson.Document;

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

	public synchronized Benutzer retrieveUser(String pseudonym) {

		MongoCollection<Document> collectionAccount = database.getCollection("account");
		// Retreive the hashed password
		Document doc = collectionAccount.find(eq("pseudonym", pseudonym)).first();
		if (doc == null) {
			System.out.println("-->null");
			return null;
		}
		System.out.println(doc.getString("storedPassword") + " " + doc.getString("pseudonym") + " 11111");
		System.out.println(doc.getString("user"));
		Benutzer benutzer = new Benutzer(pseudonym, doc.getString("storedPassword"), doc.getString("user"));
		return benutzer;
	}

	public synchronized void storeMessages(Message message) {

		MongoCollection<Document> collectionMessages = database.getCollection("messages");

		Document doc = new Document("from", message.from).append("to", message.to).append("date", message.date)
				.append("sequence", message.sequence).append("text", message.text);

		collectionMessages.insertOne(doc);
	}

	public synchronized ArrayList<Message> retrieveMessages(String user_id, int sequence, boolean delete)
			throws ParseException {
		MongoCollection<Document> collectionMessages = database.getCollection("messages");

		ArrayList<Document> list = collectionMessages.find(and(eq("to", user_id), gt("sequence", sequence)))
				.into(new ArrayList<Document>());

		if (list == null) {
			return null;
		}
		if (delete == true) {
			collectionMessages.deleteMany((and(eq("to", user_id), lte("sequence", sequence))));
		}

		ArrayList<Message> messageList = new ArrayList<Message>();

		for (Document d : list) {
			messageList.add(new Message(d.getString("token"), d.getString("from"), d.getString("to"),
					Message.stringToDate(d.getString("date")), d.getString("text"),
					Integer.parseInt(d.getString("sequence"))));
		}
		Collections.sort(messageList, new Comparator<Message>() {
			@Override
			public int compare(Message o1, Message o2) {
				return o2.sequence - o1.sequence;
			}
		});
		return messageList;

	}


	/**
	 * @see var.chat.server.persistence.StorageProvider#clearForTest()
	 */
	public void clearForTest() {
		database.getCollection("messages").drop();
		database.getCollection("account").drop();
	}
}
