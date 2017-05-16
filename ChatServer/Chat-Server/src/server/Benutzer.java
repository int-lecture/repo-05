package server;

import java.text.ParseException;
import java.util.ArrayDeque;
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Queue;
import java.util.Date;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
/**Ein einfacher Chat-Benutzer, der einen Namen als Attribut hat. In dieser
 * Klasse werden auch die Sequenznummern und Nachrichtenliste verwaltet.
 *
 * @author Santino Nobile, Sergej Kryvoruchko
 *
 **/
	public class Benutzer {
	/** Name des Benutzers */
	String name;
	
	/**Token des Benutzers */
	private String token;
	
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
		
	}
	
	
	
	
	/**
	 * Eine Methode, die Nachrichten in Json Objekte umwandelt und ein Array aus
	 * diesen Objekten ausgibt.
	 *
	 * @param sequence
	 *            - Sequenznummer die �bergeben wird.
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
			 * die Nachricht wird sonst st�ndig ausgegeben
			 */
		} else if (sequence < this.sequence) {
			for (int i = 1, n = this.sequence; n > sequence; n--) {
				msgArray[msgArray.length - i] = copy.pollLast();
				i++;
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
	 * @param sequence - übergebene Sequenznummer.
	 */
	public void deleteMsg(int sequence) {
		/*
		 * Die erste Nachricht hat bei uns die SeqNr 1. 1 ist nicht
		 * kleinergleich 0 -> die erste Nachricht wird sonst nicht gel�scht wenn
		 * die SeqNr 0 �bergeben wird.
		 */
		if (sequence == 0 && !msgliste.isEmpty()) {
			msgliste.poll();
		} else {
			/*
			 * "Nachdem der Server die Liste der Nachrichten gesendet hat,
			 * l�scht er alle Nachrichten des Nutzers, deren Sequenznummer <=
			 * der �bergebenen Zahl ist. Dieses Feature sollte allerdings f�r
			 * Testzwecke ein- und ausschaltbar sein."
			 */
			for (Message msg: msgliste) {
				if (msg.sequence <= sequence) {
					msgliste.poll();
				}
			}
		}
	}
	/**
	 * Prüft ob der Benutzer sich mit dem Token 
	 * authentifizieren kann.
	 * @return Authentifizierung klappt oder nicht.
	 */
	public boolean auth(){
		Client client = Client.create();
		JSONObject object = new JSONObject();
		try {
			object.put("token", this.token);
			object.put("pseudonym", this.name);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		ClientResponse antwort=client.resource(Chat_Server.uri + "/auth")
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, object.toString());
		client.destroy();
		if(antwort.getStatus()!=200) {
			return false;
		}
		return true;
	}
/**
 * Liest das Token des Benutzers aus.
 * @param token - ausgelesenes Token.
 */
	public void setToken(String token) {
	
		this.token=token;
	}
	
}