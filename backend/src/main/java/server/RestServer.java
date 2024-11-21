package server;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.restlet.service.LogService;

import server.ressources.User;

public class RestServer extends Application {
    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext());
        router.attach("/user", User.class);
        return router;
    }

    public static void main(String[] args) throws Exception {
        Component component = new Component();
        component.getServers().add(Protocol.HTTP, 8080);
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