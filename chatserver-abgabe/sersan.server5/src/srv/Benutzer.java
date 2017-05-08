
package srv;

import java.util.ArrayDeque;
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Queue;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

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

	/** Sequenznummer */
	int sequence;

	/** Nachrichtenliste */
	ArrayDeque<Message> msgliste = new ArrayDeque<>();

	/** Benutzer erstellen */

	public Benutzer(String name) {
		this.name = name;
		this.sequence = 0;
	}

	// public JSONArray getMessageAsJson(int sequence) throws JSONException{
	// JSONArray jArray= new JSONArray();
	// Message[] msg = msgliste.toArray(new Message[msgliste.size()]);
	//
	// if (sequence == 0) {
	// for (int i = 0; i < msg.length; i++) {
	// jArray.put(msg[i].toJ());
	// }
	// return jArray;
	// }else{
	// for (int i=(sequence+1);i<msg.length;i++){
	// jArray.put(msg[i].toJ());
	// }
	// return jArray;
	// }
	// }

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
}
