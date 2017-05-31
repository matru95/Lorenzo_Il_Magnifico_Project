package it.polimi.ingsw.gc31.server.rmiserver;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.PlayerColor;

import java.rmi.Remote;

public interface GameServer extends Remote {
    void createGame();
    GameInstance[] getGames();
    GameInstance getGame(String instanceID);
    void join(String playerName, PlayerColor color, String instanceID);
    void leave(int playerID);
}
