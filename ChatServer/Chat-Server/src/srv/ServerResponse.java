package srv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;  // new import for @context (handling the header)
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Dienste des Servers. Hier wird das Protokoll für den Nachrichten-Transfer
 * implementiert.
 *
 * @author Gruppe5
 */

/*
 * Ohne @Path("") Fehler: The ResourceConfig instance does not contain any root
 * resource classes.
 */
@Path("")
public class ServerResponse {
	/** Benutzerliste. */
	static Map<String, Benutzer> map = new HashMap<>();

	/**
	 * Abfangen einer Message des Benutzers. Wenn das Format zulässig ist sendet
	 * der Server 201.Wenn das Format nicht zulässig ist sendet der Server 400.
	 *
	 * @param jsonFormat
	 *            - Nachricht des Benutzers.
	 * @return Response - Antwort des Servers an den Client.
	 * @throws JSONException
	 *             - Bei Problemen mit Json.
	 * @throws ParseException
	 *             - Bei Problemen mit Umwandeln.
	 */
	@PUT
	@Path("/send")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putMessage(String jsonFormat) {
		Message test = null;
		JSONObject j = null;
		Date date = null;
		Benutzer benutzer = null;
		
		// to handle bad formats
		try {
			j = new JSONObject(jsonFormat);
		} catch (JSONException e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		// the issue resolved , to handle the case where the user sends a false date
		if (j.has("date")) {
			try {
				date = Message.stringToDate(j.optString("date"));
			} catch (ParseException e) {
				e.printStackTrace();
				return Response.status(Status.BAD_REQUEST).build();
			}
		} else {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		// to check whether all parameters are there
		if (j.has("from") && j.has("to") && j.has("text") && j.has("token")) {
			try {
				test = new Message(j.getString("token"), j.getString("from"), j.getString("to"), date,
						j.getString("text"), j.optInt("sequence"));
			} catch (JSONException e) {
				e.printStackTrace();
				return Response.status(Status.BAD_REQUEST).build();
			}

		} else {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		// to check whether the token is valid, or sort of authenticate the user
		if (!map.containsKey(j.optString("to"))) { // is this user already registered ?
			map.put(j.optString("to"), new Benutzer(j.optString("to"))); // if not , create a new
		}
		benutzer = map.get(j.optString("to"));
		if (Message.isJSONValid(jsonFormat) && Message.isTokenValid(test.token) && test.token != null
				&& test.from != null && test.to != null && test.date != null && test.text != null
				&& test.date.before(benutzer.expDate)) {

			test = new Message(j.optString("token"), j.optString("from"), j.optString("to"), date, j.optString("text"),
					benutzer.sequence += 1);
			benutzer.msgliste.offer(test);

			try {
				return Response.status(Status.CREATED).entity(test.datenKorrekt().toString()).build();
			} catch (JSONException e) {
				e.printStackTrace();
				return Response.status(Status.BAD_REQUEST).build();
			}

		} else if (test.date.equals(benutzer.expDate) || test.date.after(benutzer.expDate)) {
			return Response.status(Status.UNAUTHORIZED).build();
		} else if (!Message.isTokenValid(test.token)) {

			return Response.status(Status.UNAUTHORIZED).build();
		} else {
			return Response.status(Status.BAD_REQUEST).entity("Bad format").build();
		}
	}

	/**
	 * Der Client holt die Nachrichten vom Server mit GET über --> @Path.
	 *
	 * @param user_id
	 *            - Der Name des Benutzers
	 * @param sequence
	 *            - Die Sequenznummer der letzten erhaltenen Nachricht.
	 * @return Response - Antwort des Servers
	 * @throws JSONException
	 *             - Bei Problemen mit Json
	 */
	@GET
	@Context  // dafür muss ein import machen
	@Path("/messages/{user_id}/{sequence_number}/header") // hier wird der Header eingestzt
	@Produces(MediaType.APPLICATION_JSON)
	
	// new parameter added
	public Response getMessage(@PathParam("user_id") String user_id, @PathParam("sequence_number") int sequence , @Context HttpHeaders header)
			throws JSONException {
//		
//		JSONObject j = new JSONObject();
//		// the HEADER_Feld
//		String header  = j.optString("token");
		
		MultivaluedMap<String, String> mapForHeader = header.getRequestHeaders(); // create a map for the headers
		if (map.containsKey(user_id)) { // check if the user already exists

			Benutzer benutzer = map.get(user_id);
	
			if (benutzer.authenticateUser(mapForHeader.get("Authorization").get(0))) { // Der Header ist der Authorization-Header gemäß RFC2617
			    if (!map.get(user_id).msgliste.isEmpty()) {

				  //  Benutzer benutzer = map.get(user_id);
			    	JSONArray jArray;
				    jArray = benutzer.getMessageAsJson(sequence);
			    	benutzer.deleteMsg(sequence);
				   if (jArray.length() == 0) {
					   return Response.status(Status.NO_CONTENT).build();
				   }

				   return Response.status(Status.OK).entity(jArray.toString(3)).type(MediaType.APPLICATION_JSON).build();
			  }else {
				return Response.status(Status.NO_CONTENT).build();
			  } // the user has no message
			} else {
				return Response.status(Response.Status.UNAUTHORIZED).build();
	        } // authentication failed
		
	    } else {
		    return Response.status(Response.Status.BAD_REQUEST).entity("no User found.").build();
       } // the user doesn't exist
		
	
	}

	/**
	 * @see getMessage oben.
	 * @param user_id
	 * @return Response
	 * @throws JSONException
	 */
	@GET
	@Path("/messages/{user_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMessage(@PathParam("user_id") String user_id ,  @Context HttpHeaders header) throws JSONException {

		return getMessage(user_id, 0, header);
	}

}