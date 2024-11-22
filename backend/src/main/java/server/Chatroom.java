package server;

public class Chatroom {
    private final String id;
    private final String name;

    public Chatroom(String id, String name) {
        this.id = id;
        this.name = name;
    }

     public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
