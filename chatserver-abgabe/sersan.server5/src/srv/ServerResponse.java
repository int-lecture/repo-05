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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Dienste des Servers. Hier wird das Protokoll für den Nachrichten-Transfer
 * implementiert.
 *
 * @author Santino Nobile, Sergej Kryvoruchko
 */

/*
 * Ohne @Path("") Fehler: The ResourceConfig instance does not contain any root
 * resource classes.
 */
@Path("")
public class ServerResponse {

	/** String der das Datum in ISO 8601 Format umwandelt. */
	public static final String ISO8601 = "yyyy-MM-dd'T'HH:mm:ssZ";

	/** Benutzerliste. */
	static Map<String, Benutzer> map = new HashMap<>();

	/**
	 * Ein Test.
	 */
	// @Path("/helloworld")
	// @GET
	// @Produces("text/plain")
	// public String sayHello() {
	// return "Hello World!\n";
	// }
	public ServerResponse(){

	}

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
	public Response putMessage(String jsonFormat) throws JSONException, ParseException {
		
		if (Message.isJSONValid(jsonFormat)) {

			JSONObject j = null;
				j = new JSONObject(jsonFormat);
			// Wenn der Benutzer nicht vorhanden ist wird dieser neu angelegt
			if (!map.containsKey(j.optString("to"))) {
				map.put(j.optString("to"), new Benutzer(j.optString("to")));
			}

			String dateStr = (String) j.opt("date");
			SimpleDateFormat sdf = new SimpleDateFormat(ServerResponse.ISO8601);
			Date date = null;
			date = sdf.parse(dateStr);
			
			Benutzer benutzer = map.get(j.optString("to"));
			int seq = benutzer.sequence += 1;

			Message msg = new Message(j.optString("from"), j.optString("to"), date, j.optString("text"), seq);
			benutzer.msgliste.offer(msg);
			return Response.status(Status.CREATED).entity(msg.datenKorrekt().toString()).build();
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
	@Path("/messages/{user_id}/{sequence_number}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMessage(@PathParam("user_id") String user_id, @PathParam("sequence_number") int sequence) throws JSONException  {
		
		if (map.containsKey(user_id)) {

			if (!map.get(user_id).msgliste.isEmpty()) {

				Benutzer benutzer = map.get(user_id);
				JSONArray jArray;
				jArray = benutzer.getMessageAsJson(sequence);
				benutzer.deleteMsg(sequence);
				if (jArray.length() == 0) {
					return Response.status(Status.NO_CONTENT).build();
				}

				return Response.status(Status.OK).entity(jArray.toString(3)).type(MediaType.APPLICATION_JSON).build();
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
	public Response getMessage(@PathParam("user_id") String user_id) throws JSONException {

		return getMessage(user_id, 0);
	}

}
