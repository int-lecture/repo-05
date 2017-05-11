package reg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Profile {

	/** Pseudonym */
	private String pseudonym;
	/** Passwort */
	private String passwort;
	/** User Email */
	private String userEmail;
	/** Token SessionID*/
	private String token;

	private List<String> contact = new ArrayList<>();

    // Speichert ein Profil auch das Token?
	public Profile(String pseudonym, String passwort, String userEmail) {
		this.pseudonym = pseudonym;
		this.passwort = passwort;
		this.userEmail = userEmail;
	}
	public Profile(String pseudonym,String passwort,String userEmail,String token){
		this(pseudonym,passwort,userEmail);
		this.token=token;
	}


	public String getPseudonym(){
		return this.pseudonym;
	}

	public String getUserEmail(){
		return this.userEmail;
	}

	public String getToken(){
		return this.token;
	}


	public JSONObject profileToJson() throws JSONException{
		JSONObject j = new JSONObject();
		j.put("name", this.pseudonym);
		j.put("email", this.userEmail);
		j.put("contact", Arrays.toString(contact.toArray(new String[contact.size()])));

		return j;
	}


}