package server;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
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
@Path("")
public class ChatServer {

	StorageProviderMongoDB db = new StorageProviderMongoDB();
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
	public Response putMessage(String jsonFormat) throws ParseException {
		Message message = null;
		JSONObject j = null;
		Date date = null;
		Benutzer benutzer = null;
		if(Message.isMessageValid(jsonFormat)){
		try {
			j = new JSONObject(jsonFormat);
			date = Message.stringToDate(j.optString("date"));
			} catch (JSONException | ParseException e1) {
			e1.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
			}

			benutzer = db.retrieveUser(j.optString("from"));

			//Wenn der User nicht in der DB ist
			if(benutzer==null){
				return Response.status(Status.UNAUTHORIZED).build();
			}

			benutzer.setToken(j.optString("token"));

			try{
			if(!benutzer.auth()){
				return Response.status(Status.UNAUTHORIZED).build();
			}
			}catch (RuntimeException e){
			e.printStackTrace();
			return Response.status(Status.UNAUTHORIZED).build();
			}

			message = new Message(j.optString("token"), j.optString("from"),
					j.optString("to"), date, j.optString("text"),
					db.getUpdatedSequence(j.optString("to")));

			db.storeMessages(message);
			try {
				return Response.status(Status.CREATED).header("Access-Control-Allow-Origin", "*").entity(message.datenKorrekt().toString()).build();
			} catch (JSONException e) {
				e.printStackTrace();
				return Response.status(Status.BAD_REQUEST).build();
			}
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
	 * @throws ParseException
	 * @throws JSONException
	 *             - Bei Problemen mit Json
	 */
	@GET
	@Path("/messages/{user_id}/{sequence_number}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMessage(@PathParam("user_id") String user_id, @PathParam("sequence_number") int sequence,
			@Context HttpHeaders header) throws ParseException {
		JSONArray jArray = new JSONArray();
		MultivaluedMap<String, String> hmap = header.getRequestHeaders();
		String token = hmap.get("Authorization").get(0).substring(6);
		System.out.println(token);
		if (hmap.get("Authorization") == null || hmap.get("Authorization").isEmpty()) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		Benutzer benutzer = db.retrieveUser(user_id);
		if (benutzer != null) {
			List<Message> messageList = db.retrieveMessages(user_id, sequence, true);
			if (messageList != null) {
				benutzer.setToken(token);
				if (!benutzer.auth()) {
					return Response.status(Status.UNAUTHORIZED).build();
				}
				try {
					for (Message m : messageList) {
						jArray.put(m.toJson());
					}
				} catch (JSONException e) {
					e.printStackTrace();
					return Response.status(Status.BAD_REQUEST).build();
				}
				try {
					System.out.println(jArray.toString());
					return Response.status(Status.OK).header("Access-Control-Allow-Origin", "*")
							.entity(jArray.toString(3)).type(MediaType.APPLICATION_JSON).build();
				} catch (JSONException e) {
					e.printStackTrace();
					return Response.status(Status.BAD_REQUEST).build();
				}
			} else {
				return Response.status(Status.NO_CONTENT).header("Access-Control-Allow-Origin", "*").build();
			}
		} else {
			return Response.status(Status.NO_CONTENT).header("Access-Control-Allow-Origin", "*").build();
		}
	}

	/**
	 * @see getMessage oben.
	 * @param user_id
	 * @return Response
	 * @throws JSONException
	 * @throws ParseException
	 */
	@GET
	@Path("/messages/{user_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMessage(@PathParam("user_id") String user_id, @Context HttpHeaders header) throws JSONException, ParseException {

		return getMessage(user_id, 0, header);
	}


	@OPTIONS
	@Path("/send")
	public Response optionsReg() {
	    return Response.ok("")
	            .header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209600")
	            .build();
	}

	@OPTIONS
	@Path("/messages/{userid}/{sequenceNumber}")
	public Response optionsProfileWithSeqNumber() {
	    return Response.ok("")
	            .header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209600")
	            .build();
	}

	@OPTIONS
	@Path("/messages/{userid}")
	public Response optionsProfile() {
		return Response.ok("")
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Max-Age", "1209600")
				.build();
	}

}
