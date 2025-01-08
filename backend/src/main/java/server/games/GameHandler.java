package server.games;

import server.database.DatabaseHandler;

import java.util.HashMap;

public class GameHandler {
    private static GameHandler instance = new GameHandler();

    private HashMap<String, GameInstance> runningGames = new HashMap<>();

    private GameHandler() {}

    public static GameHandler getInstance() {
        if (instance == null) {
            instance = new GameHandler();
        }
        return instance;
    }

    public String createGame() {
        String id = DatabaseHandler.generateId(6);

        this.runningGames.put(id, new GameInstance());

        return id;
    }
}
