
package srv;

//import java.awt.PageAttributes.MediaType;
import javax.ws.rs.core.MediaType;  // accept(MediaType.APPLICATION_JSON)
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Queue;
import java.util.Date;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;

/**
 * Ein einfacher Chat-Benutzer, der einen Namen als Attribut hat. In dieser
 * Klasse werden auch die Sequenznummern und Nachrichtenliste verwaltet.
 *
 * @author Santino Nobile, Sergej Kryvoruchko
 *
 **/
public class Benutzer {
	/** Name des Benutzers */
	String name;
	
	/**Token des Benutzers */
	String token;
	
	private static final String url = "http://localhost:5001";
	
	/** Auslaufdatum des Tokens*/
	Date expDate;

	/** Sequenznummer */
	int sequence;

	/** Nachrichtenliste */
	ArrayDeque<Message> msgliste = new ArrayDeque<>();

	/** Benutzer erstellen */
	public Benutzer(String name) {
		this.name = name;
		this.sequence = 0;
		try {
			this.expDate=Message.stringToDate("2018-05-08T11:30:36+0200");
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}
	}
	/**
	 * Eine Methode, die Nachrichten in Json Objekte umwandelt und ein Array aus
	 * diesen Objekten ausgibt.
	 *
	 * @param sequence
	 *            - Sequenznummer die übergeben wird.
	 * @return jArray - Ein Array aus Json Objekten der Nachrichten
	 * @throws JSONException
	 *             - Wird geworfen, falls es Probleme mit der Umwandlung gibt
	 */
	public JSONArray getMessageAsJson(int sequence) throws JSONException {
		JSONArray jArray = new JSONArray();
		Message[] msgArray = new Message[this.sequence - sequence];
		ArrayDeque<Message> copy = msgliste.clone();
		if (sequence == 0) {
			for (Message msg : msgliste) {
				jArray.put(msg.toJson());
			}
			return jArray;
			/*
			 * die Nachricht wird sonst ständig ausgegeben
			 */
		} else if (sequence < this.sequence) {
			for (int i=0; i<msgArray.length;i++) {
				msgArray[msgArray.length - i] = copy.pollLast();
			}
			for (Message msg: msgArray) {
				jArray.put(msg.toJson());
			}
			return jArray;
		}
		return jArray;
	}

	/**
	 * Löscht die Nachrichten deren Sequenznummer kleiner oder gleich der
	 * übergebenen Sequenznummer sind.
	 *
	 * @param sequence
	 *            - übergebene Sequenznummer.
	 */
	public void deleteMsg(int sequence) {
		/*
		 * Die erste Nachricht hat bei uns die SeqNr 1. 1 ist nicht
		 * kleinergleich 0 -> die erste Nachricht wird sonst nicht gelöscht wenn
		 * die SeqNr 0 übergeben wird.
		 */
		if (sequence == 0 && !msgliste.isEmpty()) {
			msgliste.poll();
		} else {
			/*
			 * "Nachdem der Server die Liste der Nachrichten gesendet hat,
			 * löscht er alle Nachrichten des Nutzers, deren Sequenznummer <=
			 * der übergebenen Zahl ist. Dieses Feature sollte allerdings für
			 * Testzwecke ein- und ausschaltbar sein."
			 */
			for (Message msg: msgliste) {
				if (msg.sequence <= sequence) {
					msgliste.poll();
				}
			}
		}
	}
	
	// TODO the method to authenticate users
	/*
	 * // to authenticate the user
	 * @return true or false depending on whether the user entered the right 
	 */
	public boolean authenticateUser(String token) {
		SimpleDateFormat sdf = new SimpleDateFormat(Message.ISO8601);
		System.out.println(token);
		if (this.token == token) {
			if (sdf.format(new Date()).compareTo(expDate.toString()) < 0) {
				return true;
			}
		}

		JSONObject obj = new JSONObject();
		try {
			obj.put("token", token);
			obj.put("pseudonym", name);
			System.out.println("Authentifiziere "+name+"  "+ token);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Client client = Client.create();
		String response;
		try{
		response = client.resource(url + "/auth").accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).post(String.class, obj.toString());
		client.destroy();
		}catch(RuntimeException e){
			return false;
		}
		try {
			JSONObject jo = new JSONObject(response);
			if (jo.get("success").equals("true")) {
				this.expDate = sdf.parse(jo.getString("expire-date"));
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
}