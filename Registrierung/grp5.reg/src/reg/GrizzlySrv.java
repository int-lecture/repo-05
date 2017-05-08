package reg;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;
/**
 *
 * Ein Grizzly Server.
 *
 */
public class GrizzlySrv {

	public static void main(String[] args) throws IllegalArgumentException, IOException {
		final String baseUri = "http://localhost:5001/";
		final String paket = "srv";

		final Map<String, String> initParams = new HashMap<String, String>();

		initParams.put("com.sun.jersey.config.property.packages", paket);

		System.out.println("Starte grizzly...");
		SelectorThread threadSelector = GrizzlyWebContainerFactory.create(baseUri, initParams);

		System.out.printf("Grizzly läuft unter %s%n", baseUri);
		System.out.println("[ENTER] drücken, um Grizzly zu beenden");

		System.in.read();
		threadSelector.stopEndpoint();

		System.out.println("Grizzly wurde beendet");
		System.exit(0);

	}

}
