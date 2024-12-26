package server;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.restlet.service.LogService;
import server.ressources.*;

public class RestServer extends Application {
    public static int REST_PORT = 8080;
    public static int SOCKET_PORT = 8081;

    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext());
        router.attach("/user", UserRessource.class);
        router.attach("/chatroom", ChatroomRessource.class);
        router.attach("/message", MessageRessource.class);
        router.attach("/test", TestRessource.class);
        return router;
    }

    public static void main(String[] args) throws Exception {
        Component component = new Component();
        component.getServers().add(Protocol.HTTP, "0.0.0.0", REST_PORT);
        component.getDefaultHost().attach(new RestServer());
        component.setLogService(new LogService(false));
        component.start();

        Receiver receiver = new Receiver();
        receiver.start();
    }

    public static void log(String message) {
        System.out.println(">>>>> " + message);
    }
}