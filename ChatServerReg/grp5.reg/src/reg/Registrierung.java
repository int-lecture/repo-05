package reg;


import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;



@Path("/")
public class Registrierung {

	/** Profile der registrierten User*/

	static List<Profile> profile = new ArrayList<>();

	/**
	 * Registriert einen neuen User.
	 * @param jsonFormat Daten die f�r die Registrierung notwendig sind.
	 * @return Response HTTP Antwort
	 * @throws JSONException
	 */

	@PUT
	@Path("register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)

	public Response register(String jsonFormat) throws JSONException {
		if (isJSONValid(jsonFormat)) {
			JSONObject j = new JSONObject(jsonFormat);
			if (j.optString("pseudonym") != null && j.optString("passwort") != null && j.optString("user") != null) {
				// Wenn Liste nicht leer
				if (!profile.isEmpty()) {
					// Schauen ob es Pseudonym schon gibt

					for (Profile profil : profile) {
						if (j.optString("pseudonym").equals(profil.getPseudonym())) {
							// Es gibt Pseudonym bereits
							// TODO: 418 "I'm a teapot"
							return Response.status(Status.NOT_ACCEPTABLE).build();
						}
					}
				}
				Profile profil = new Profile(j.optString("pseudonym"), j.optString("user"));
				profile.add(profil);
				String userData = "";
				try {
					userData = SecurityHelper.hashPassword(j.optString("password"));
				} catch (NoSuchAlgorithmException | InvalidKeySpecException i) {
					// TODO Auto-generated catch block
					i.printStackTrace();
				}
				userData = userData + j.optString("user");
				StorageProviderMongoDB.storePassword(userData);
				JSONObject ok = new JSONObject();
				ok.put("success", true);
				return Response.status(Status.OK).header("Access-Control-Allow-Origin", "*").entity(ok.toString()).type(MediaType.APPLICATION_JSON).build();

			} else {
				return Response.status(Status.BAD_REQUEST).build();
			}
		} else {
			return Response.status(Status.BAD_REQUEST).build();
		}

	}
	 /**
     * Pr�ft ob der �bergebene String in ein JSONObjekt
     * abgespeichert werden kann.
     * Quelle:http://stackoverflow.com/questions/10174898/how-to-check-whether-a-given-string-is-valid-json-in-java
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
     * Liefert bei g�ltigem Token das Profil des Users zur�ck.
     * @param jsonFormat Daten die f�r die Profilanfrage notwending sind.
     * @return Response HTTP Antwort.
     * @throws JSONException
     */

    @Path("profile")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getProfile(String jsonFormat) throws JSONException{


    	if(isJSONValid(jsonFormat)){
    		JSONObject j = new JSONObject(jsonFormat);

    		if(!j.isNull("token") && !j.isNull("getownprofile") ){
    			Profile nutzer=null;

    			for(Profile profil:profile){
					if(j.optString("getownprofile").equals(profil.getPseudonym())){
						nutzer=profil;
					}
				}
    			//Token validierung
    			if(nutzer!=null){
//    				String url = "http://localhost:5001";
//    				Client client = Client.create();
//    				ClientResponse response=client.resource(url + "/auth")
//    		            .accept(MediaType.APPLICATION_JSON)
//    		            .type(MediaType.APPLICATION_JSON)
//    		            .post(ClientResponse.class,j.toString());
//
//    				if (response.getStatus() != 200) {
//    					return Response.status(Status.BAD_REQUEST).build();
//    		        }
//    				return Response.status(Status.OK).entity(nutzer.profileToJson().toString(3)).type(MediaType.APPLICATION_JSON).build();

    				String data=StorageProviderMongoDB.retrieveToken(j.optString("token"), j.optString("pseudonym"));
    				if(data!=null){
    					return Response.status(Status.OK).header("Access-Control-Allow-Origin", "*").entity(nutzer.profileToJson().toString(3)).type(MediaType.APPLICATION_JSON).build();
    				}
    				return Response.status(Status.UNAUTHORIZED).build();
    			}else{
    				return Response.status(Status.NO_CONTENT).build();
    			}
    		}
    		return Response.status(Status.BAD_REQUEST).build();
    	}else{
    		return Response.status(Status.BAD_REQUEST).build();
    	}

    }


	@OPTIONS
	@Path("/register")
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
	@Path("/profile")
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
