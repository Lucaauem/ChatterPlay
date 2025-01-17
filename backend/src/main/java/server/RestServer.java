package server;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.restlet.service.LogService;
import server.database.DatabaseHandler;
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
        router.attach("/game", GameRessource.class);
        router.attach("/test", TestRessource.class);
        return router;
    }

    public static void main(String[] args) throws Exception {
        Component component = new Component();
        component.getServers().add(Protocol.HTTP, "0.0.0.0", REST_PORT);
        component.getDefaultHost().attach(new RestServer());
        component.setLogService(new LogService(false));
        component.start();

        System.out.println("===============================================");
        logStartup("STARTED CHATTERPLAY BACKEND ON PORT " + REST_PORT);

        // Test db connection
        boolean canConnectToDatabse = DatabaseHandler.getInstance().testConnection();
        logStartup(canConnectToDatabse ? "CONNECTED TO DATABSE" : "COULD NOT CONNECT TO DATABSE", !canConnectToDatabse);

        // Start socket port
        Receiver receiver = new Receiver();
        receiver.start();
        RestServer.logStartup("RECEIVER THREAD STARTED ON PORT " + RestServer.SOCKET_PORT);

        logStartup("BACKEND OPERATIONAL");
        System.out.println("===============================================");
    }

    public static void log(String message) {
        System.out.println(">>>>> " + message);
    }

    public static void logStartup(String message) {
        logStartup(message, false);
    }

    public static void logStartup(String message, int indentatiosn) {
        System.out.print(new String(new char[indentatiosn]).replace("\0", "= "));
        logStartup(message);
    }

    public static void logStartup(String message, boolean isError) {
        if(isError) {
            System.err.println("= > " + message);
            System.err.println("ABORT STARTUP");
            System.exit(1);
            return;
        }
        System.out.println("= > " + message);
    }
}