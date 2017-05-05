package srv;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class Message {
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
        SimpleDateFormat sdf = new SimpleDateFormat(ServerResponse.ISO8601);

        return String.format("{ 'from': '%s', 'to': '%s', 'date': '%s', 'text': '%s'}".replace('\'',  '"'),
                from, to, sdf.format(new Date()), text);
    }



    /**
     * Prüft ob der übergebene String in ein JSONObjekt
     * abgespeichert werden kann.
     *
     * @param test - Der String der geprüft wird.
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
    	SimpleDateFormat sdf = new SimpleDateFormat(ServerResponse.ISO8601);
    	obj.put("date",sdf.format(date));
    	obj.put("sequence",this.sequence);
    	return obj;
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
    	SimpleDateFormat sdf = new SimpleDateFormat(ServerResponse.ISO8601);

    	obj.put("from", from);
    	obj.put("to", to);
    	obj.put("date",sdf.format(date));
    	obj.put("text",text);
    	obj.put("sequence", sequence);
    	return obj;
    }
}





