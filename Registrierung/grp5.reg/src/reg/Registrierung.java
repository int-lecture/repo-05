package reg;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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



@Path("/")
public class Registrierung {

	static List<Profile> profile = new ArrayList<>();
	/**
	 *
	 * @param jsonFormat
	 * @return
	 * @throws JSONException
	 */

	@PUT
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)

	public Response register(String jsonFormat) throws JSONException{

		if(isJSONValid(jsonFormat)){
			JSONObject j = new JSONObject(jsonFormat);
			if(j.optString("pseudonym")!=null && j.optString("passwort")!=null &&j.optString("user")!=null){
				//Wenn Liste nicht leer
				if(!profile.isEmpty()){
					//Schauen ob es Pseudonym schon gibt
					for(Profile profil:profile){
						if(j.optString("pseudonym")==profil.getPseudonym()){
							//Es gibt Pseudonym bereits
							return Response.status(Status.BAD_REQUEST).build();
						}
					}
					//Pseudonym gibt es nicht

					Profile profil = new Profile(j.optString("pseudonym"),j.optString("passwort"),j.optString("user"));
					profile.add(profil);
					JSONObject ok = new JSONObject();
					ok.put("success", true);
					return Response.status(Status.OK).entity(ok.toString()).type(MediaType.APPLICATION_JSON).build();

				//Wenn Liste leer
				}else{
					Profile profil = new Profile(j.optString("pseudonym"),j.optString("passwort"),j.optString("user"));
					profile.add(profil);
					JSONObject ok = new JSONObject();
					ok.put("success", true);
					return Response.status(Status.OK).entity(ok.toString()).type(MediaType.APPLICATION_JSON).build();
				}

			}else{
				return Response.status(Status.BAD_REQUEST).entity("Felder dürfen nicht leer sein!").build();
			}
		}else{
			return Response.status(Status.BAD_REQUEST).entity("Bad format").build();
		}


	}

	 /**
     * Prüft ob der übergebene String in ein JSONObjekt
     * abgespeichert werden kann.
     * Quelle:http://stackoverflow.com/questions/10174898/how-to-check-whether-a-given-string-is-valid-json-in-java
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
	
    //TODO Profilanfragen (eigener Server für Profile?)
    @Path("/profile")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getProfile(String jsonFormat) throws JSONException{
    	if(isJSONValid(jsonFormat)){
    		JSONObject j = new JSONObject(jsonFormat);
    		if(j.optString("token")!=null && j.optString("getownprofile")!=null ){

    			Profile nutzer=null;
    			for(Profile profil:profile){
					if(j.optString("pseudonym")==profil.getPseudonym()){
						nutzer=profil;
					}
				}
    			if(nutzer==null){
    				return Response.status(Status.BAD_REQUEST).entity("Unknown").build();
    			}
    			if(nutzer.getToken()==j.optString("token")){
    				return Response.status(Status.OK).entity(nutzer.profileToJson().toString(3)).type(MediaType.APPLICATION_JSON).build();
    			}
    		}
    		return Response.status(Status.BAD_REQUEST).entity("Bad token lol").build();
    	}else{
    		return Response.status(Status.BAD_REQUEST).entity("Bad format").build();
    	}

    }

}

}
