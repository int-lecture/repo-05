package server;

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
public class GrizzlyServer {

	public static void main(String[] args) throws IllegalArgumentException, IOException {
		final String baseUri = "http://localhost:";
		final String paket = "server";
		final Map<String, String> initParams = new HashMap<String, String>();

		initParams.put("com.sun.jersey.config.property.packages", paket);

		System.out.println("Starte grizzly...");

		SelectorThread threadSelector1 = GrizzlyWebContainerFactory.create(baseUri+"4000/", initParams);
		SelectorThread threadSelector2 = GrizzlyWebContainerFactory.create(baseUri+"4001/", initParams);
		SelectorThread threadSelector3 = GrizzlyWebContainerFactory.create(baseUri+"4002/", initParams);

		System.out.printf("Grizzlys laufen unter die Poeerts %s %s %s %n", baseUri+"4000/",baseUri+"4001/",baseUri+"4002/");
		System.out.println("[ENTER] drücken, um Grizzly zu beenden");

		System.in.read();
		threadSelector1.stopEndpoint();
		threadSelector2.stopEndpoint();
		threadSelector3.stopEndpoint();

		System.out.println("Grizzlys wurden getötet :(");
		System.exit(0);

	}

}