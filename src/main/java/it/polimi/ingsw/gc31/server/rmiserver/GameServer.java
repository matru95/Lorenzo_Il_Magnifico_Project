package it.polimi.ingsw.gc31.server.rmiserver;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.PlayerColor;

import java.rmi.Remote;
import java.util.ArrayList;
import java.util.UUID;

public interface GameServer extends Remote {
    void createGame();
    ArrayList<GameInstance> getGames();
    GameInstance getGame(UUID instanceID);
    void join(UUID playerID, String playerName, PlayerColor color, UUID instanceID);
    void leave(UUID playerID);
}
