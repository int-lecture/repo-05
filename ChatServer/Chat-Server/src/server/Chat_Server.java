package server;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
 * Dienste des Servers. Hier wird das Protokoll f�r den Nachrichten-Transfer
 * implementiert.
 * 
 * @author Gruppe5
 */
@Path("")
public class Chat_Server {
	/** Benutzerliste. */
	static Map<String, Benutzer> map = new HashMap<>();
	/**
	 * Abfangen einer Message des Benutzers. Wenn das Format zul�ssig ist sendet
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
		Message message = null;
		JSONObject j = null;
		Date date = null;
		Benutzer benutzer = null;
		if(Message.validierung(jsonFormat)){
		try {
			j = new JSONObject(jsonFormat);
			date = Message.stringToDate(j.optString("date"));
			} catch (JSONException | ParseException e1) {
			e1.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (!map.containsKey(j.optString("to"))) {
			map.put(j.optString("to"), new Benutzer(j.optString("to")));
			}
			benutzer = map.get(j.optString("to"));
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
					benutzer.sequence += 1);
			benutzer.msgliste.offer(message);
			try {
				return Response.status(Status.CREATED).entity(message.datenKorrekt().toString()).build();
			} catch (JSONException e) {
				e.printStackTrace();
				return Response.status(Status.BAD_REQUEST).build();
			}
		}
		 else {
			return Response.status(Status.BAD_REQUEST).entity("Bad format").build();
		}
	}
	
	/**
	 * Der Client holt die Nachrichten vom Server mit GET �ber --> @Path.
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
	@Path("/messages/{user_id}/{sequence_number}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMessage(@PathParam("user_id") String user_id, @PathParam("sequence_number") int sequence,
			@Context HttpHeaders header) {
		JSONArray jArray = null;
		MultivaluedMap<String, String> hmap = header.getRequestHeaders();
		String token = hmap.get("Authorization").get(0).substring(6);
			if (hmap.get("Authorization") == null || hmap.get("Authorization").isEmpty()) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		if (map.containsKey(user_id)) {
			if (!map.get(user_id).msgliste.isEmpty()) {
				Benutzer benutzer = map.get(user_id);
				benutzer.setToken(token);
				if(!benutzer.auth()){
					return Response.status(Status.UNAUTHORIZED).build();
				}try {
							jArray = benutzer.getMessageAsJson(sequence);
						} catch (JSONException e) {
							e.printStackTrace();
							return Response.status(Status.BAD_REQUEST).build();
						}
						benutzer.deleteMsg(sequence);
						if (jArray.length() == 0) {
							return Response.status(Status.NO_CONTENT).build();
						}
						try {
							return Response.status(Status.OK).entity(jArray.toString(3))
									.type(MediaType.APPLICATION_JSON).build();
						} catch (JSONException e) {
							e.printStackTrace();
							return Response.status(Status.BAD_REQUEST).build();
						}
				 } else {
				return Response.status(Status.NO_CONTENT).build();
			}
		} else {
			return Response.status(Status.NO_CONTENT).build();
		}
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
	public Response getMessage(@PathParam("user_id") String user_id, @Context HttpHeaders header) throws JSONException {

		return getMessage(user_id, 0, header);
	}

}