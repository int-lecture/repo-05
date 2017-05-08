package reg;

import java.util.ArrayList;
import java.util.List;

public class Profile {

	/** Pseudonym */
	private String pseudonym;
	/** Passwort */
	private String passwort;
	/** User Email */
	private String userEmail;

	private List<String> contact = new ArrayList<>();


	public Profile(String pseudonym, String passwort, String userEmail) {
		this.pseudonym = pseudonym;
		this.passwort = passwort;
		this.userEmail = userEmail;
	}

	public String getPseudonym(){
		return this.pseudonym;
	}
}
