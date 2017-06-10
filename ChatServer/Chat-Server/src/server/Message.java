package server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.apache.commons.codec.binary.Base64;

public class Message {

	/** String der das Datum in ISO 8601 Format umwandelt. */
	public static final String ISO8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	/**Token des Benutzers*/
	String token;
	  /** From. */
    String from;

    /** To. */
    String to;

    /** Date. */
    Date date;

    /** Text. */
    String text;

    /** Sequence number. */
    int sequence;

    /**
     * Create a new message.
     *
     * @param from From.
     * @param to To.
     * @param date Date.
     * @param text Contents.
     * @param sequence Sequence-Number.
     */
    public Message(String from, String to, Date date, String text,
            int sequence) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.text = text;
        this.sequence = sequence;
    }
    public Message(String token, String from, String to, Date date, String text,
            int sequence) {
    	this(from,to,date,text,sequence);
    	this.token=token;
    }

    /**
     * Create a new message.
     *
     * @param from From.
     * @param to To.
     * @param date Date.
     * @param text Contents.
     */
    public Message(String from, String to, Date date, String text) {
        this(from, to, date, text, 0);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat(ISO8601);

        return String.format("{ 'from': '%s', 'to': '%s', 'date': '%s', 'text': '%s'}".replace('\'',  '"'),
                from, to, sdf.format(new Date()), text);
    }
    /**
     * Prüft ob der übergebene String in ein JSONObjekt
     * abgespeichert werden kann.
     *
     * @param test - Der String der gepr�ft wird.
     * @return boolean - wahr oder falsch.
     */
    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Wandelt ein Date Objekt in ein String um.
     * @param date
     * @return Date Zeichenkette in ISO8601 format
     */
    public static String dateToString(Date date){
    	return new SimpleDateFormat(ISO8601).format(date);
    }

    /**
     * Wandelt einen Datestring in ein Date Objekt um.
     * @param date
     * @return Date Objekt
     * @throws ParseException
     */
    public static Date stringToDate(String date) throws ParseException{
    	return new SimpleDateFormat(ISO8601).parse(date);
    }
    /**
     * Bei korrekter Formattierung der gesendeter Nachricht
     * wird dieses Objekt als Antwort-Json an den Client gesendet.
     *
     * @return JSONObject - Objekt mit dem Datum und der Sequenznummer,
     * 						das gesendet wird.
     * @throws JSONException - Wird geworfen, falls bei der Erstellung des
     * 						   Json Objektes Probleme entstehen
     */
    public JSONObject datenKorrekt() throws JSONException{

    	JSONObject obj = new JSONObject();
    	SimpleDateFormat sdf = new SimpleDateFormat(ISO8601);
    	obj.put("date",sdf.format(date));
    	obj.put("sequence",this.sequence);
    	return obj;
    }
    /**
     * Prüft ob die Nachricht korrekt formatiert ist.
     * @param nachricht
     * @return
     */
    public static boolean isMessageValid(String nachricht){
    	Message message= null;
    	JSONObject test;
    	Date date=null;
		try {
			test = new JSONObject(nachricht);
		} catch (JSONException e1) {
			e1.printStackTrace();
			return false;
		}
    	if (test.has("from") && test.has("to")&& test.has("date") && test.has("text") && test.has("token") ) {
			try {
				date = Message.stringToDate(test.optString("date"));
				message = new Message(test.getString("token"), test.getString("from"), test.getString("to"), date,
						test.getString("text"), test.optInt("sequence"));
			} catch (ParseException |JSONException e) {
				e.printStackTrace();
				return false;
			}
			if (Message.isJSONValid(nachricht) && message.token != null && message.from != null
					&& message.to != null && message.date != null && message.text != null) {
				return true;
			}
		} else {
			return false;
		}

    	return true;
    }
    /**
     * Speichert die Daten eines Messageobjekts
     * in ein JSONObjekt
     *
     * @return JSONObjekt
     * @throws JSONException
     */
    public JSONObject toJson() throws JSONException{
    	JSONObject obj = new JSONObject();
    	obj.put("token",this.token);
    	obj.put("from", from);
    	obj.put("to", to);
    	obj.put("date",dateToString(this.date));
    	obj.put("text",text);
    	obj.put("sequence", sequence);
    	return obj;
    }
}