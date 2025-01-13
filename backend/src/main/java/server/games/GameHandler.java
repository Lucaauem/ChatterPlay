package server.games;

import server.database.DatabaseHandler;
import java.util.HashMap;

public class GameHandler {
    private static GameHandler instance;

    private final HashMap<String, GameInstance> runningGames = new HashMap<>();

    private GameHandler() {}

    public static GameHandler getInstance() {
        if (instance == null) {
            instance = new GameHandler();
        }
        return instance;
    }

    public String createGame(GameType gameType, String creatorId, String oponentId) {
        String id = DatabaseHandler.generateId(6);

        this.runningGames.put(id, new GameInstance(gameType, creatorId, oponentId));

        return id;
    }

    public GameInstance getGame(String id) {
        return this.runningGames.get(id);
    }
}
