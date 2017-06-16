package server;


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
import static com.mongodb.client.model.Filters.gte;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



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
		System.out.println(doc.getString("storedPassword") + " " + doc.getString("pseudonym") + "DB");
		System.out.println(doc.getString("user"));
		Benutzer benutzer = new Benutzer(pseudonym, doc.getString("storedPassword"), doc.getString("user"));
		return benutzer;
	}
	public synchronized int getUpdatedSequence(String user_id){
		MongoCollection<Document> collectionSequence = database.getCollection("sequence");
		Document doc=collectionSequence.find(eq("pseudonym",user_id)).first();
		if(doc!=null){
			doc.replace("sequence", doc.getInteger("sequence")+1);
			collectionSequence.deleteOne(eq("pseudonym",user_id));
			collectionSequence.insertOne(doc);
			return doc.getInteger("sequence");
		}else{
			Document doc1=new Document("sequence",0).append("pseudonym", user_id);
			collectionSequence.insertOne(doc1);
			return 0;
		}
	}


	public synchronized void storeMessages(Message message) {

		MongoCollection<Document> collectionMessages = database.getCollection("messages");

		Document doc = new Document("from", message.from).append("to", message.to).append("date", message.date)
				.append("sequence", message.sequence).append("text", message.text);

		collectionMessages.insertOne(doc);
	}

	public synchronized List<Message> retrieveMessages(String user_id, int sequence, boolean delete)
			throws ParseException {
		MongoCollection<Document> collectionMessages = database.getCollection("messages");

		List<Document> list = (List<Document>) collectionMessages.find(and(eq("to", user_id), gte("sequence", sequence)))
				.into(new ArrayList<Document>());

		if (list == null) {
			return null;
		}
		if (delete == true) {
			collectionMessages.deleteMany((and(eq("to", user_id))));
		}

		List<Message> messageList = new ArrayList<Message>();

		for (Document d : list) {
			messageList.add(new Message(d.getString("token"), d.getString("from"), d.getString("to"),
					d.getDate("date"), d.getString("text"),
					d.getInteger("sequence")));
		}
		Collections.sort(messageList, new Comparator<Message>() {
			@Override
			public int compare(Message o1, Message o2) {
				return o1.sequence - o2.sequence;
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
