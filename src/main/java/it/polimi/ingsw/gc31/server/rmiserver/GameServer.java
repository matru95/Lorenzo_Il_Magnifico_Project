package it.polimi.ingsw.gc31.server.rmiserver;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.PlayerColor;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.UUID;

public interface GameServer extends Remote {
    void createGame() throws RemoteException, NoResourceMatch;
    ArrayList<GameInstance> getGames() throws RemoteException;
    GameInstance getGame(UUID instanceID) throws RemoteException;
    void join(UUID playerID, String playerName, PlayerColor color, UUID instanceID) throws RemoteException;
    void leave(UUID playerID) throws RemoteException;
}
