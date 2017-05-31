package it.polimi.ingsw.gc31.server.rmiserver;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.PlayerColor;

import java.util.ArrayList;
import java.util.UUID;

public class GameServerImpl implements GameServer{
    ArrayList<GameInstance> games;

    public GameServerImpl() {
        games = new ArrayList<>();
    }

    @Override
    public void createGame() {

    }

    @Override
    public GameInstance[] getGames() {
        return new GameInstance[0];
    }

    @Override
    public GameInstance getGame(UUID instanceID) {
        return null;
    }

    @Override
    public void join(UUID playerID, String playerName, PlayerColor color, UUID instanceID) {

    }

    @Override
    public void leave(UUID playerID) {

    }
}
