package server.games;

import server.RestServer;

public class GameInstance {
    private final GameType type;

    public GameInstance(GameType type) {
        this.type = type;
    }

    public void makeTurn(String move) {
        switch (type) {
            case CONNECT_FOUR -> onFourConnect(move);
            case TIC_TAC_TOE -> onTicTacToe(move);
        }
    }

    private void onTicTacToe(String move) {
        RestServer.log("TicTacToe: " + move);
    }

    private void onFourConnect(String move) {
        RestServer.log("FourConnect: " + move);
    }
}
