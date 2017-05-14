package reg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Profile {

	/** Pseudonym */
	private String pseudonym;
	/** User Email */
	private String userEmail;
	/** Token */
	private String token;
	/** Kontaktliste */
	private List<String> contacts = new ArrayList<>();

	/** Profil erstellen */
	public Profile(String pseudonym, String userEmail) {
		this.pseudonym = pseudonym;
		this.userEmail = userEmail;
	}

	/** Profil erstellen mit Token */
	public Profile(String pseudonym, String userEmail, String token) {
		this(pseudonym, userEmail);
		this.token = token;
	}

	public String getPseudonym() {
		return this.pseudonym;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public String getToken() {
		return this.token;
	}

	/**
	 * Fügt Kontakt hinzu wenn nicht vorhanden.
	 *
	 * @param contact Kontakt der hinzugefügt werden soll.
	 */
	public void addContact(String contact) {
		if (!contacts.contains(contact)) {
			contacts.add(contact);
		}
	}

	/**
	 * Löscht Kontakt.
	 *
	 * @param contact Kontakt der gelöscht werden soll.
	 */
	public void removeContact(String contact) {
		contacts.remove(contact);
	}

	/**
	 * Wandelt die Kontaktliste in ein JSONArray um.
	 *
	 * @param contacts Kontaktliste.
	 * @return JSONArray
	 */
	private JSONArray contactsToJSONArray(String[] contacts) {
		JSONArray j = new JSONArray();

		for (int i = 0; i < contacts.length; i++) {
			j.put(contacts[i]);
		}
		return j;
	}

	/**
	 * Speichert die Profildaten in ein JSONObject.
	 *
	 * @return JSONObject
	 * @throws JSONException
	 */
	public JSONObject profileToJson() throws JSONException {
		JSONObject j = new JSONObject();
		j.put("name", this.pseudonym);
		j.put("email", this.userEmail);
		// Zum Testen
		// contacts.add("john");
		// contacts.add("susi");
		j.put("contact", contactsToJSONArray(contacts.toArray(new String[contacts.size()])));

		return j;
	}

}
