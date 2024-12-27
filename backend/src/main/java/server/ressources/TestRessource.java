package server.ressources;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class TestRessource extends ServerResource {
	@Get
	public void testConnection() {
		// Use this ressource to test if the client can connect to the server
	}
}
