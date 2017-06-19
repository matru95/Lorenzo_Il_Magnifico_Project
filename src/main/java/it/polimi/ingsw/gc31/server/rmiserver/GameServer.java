package it.polimi.ingsw.gc31.server.rmiserver;

import it.polimi.ingsw.gc31.model.GameInstance;
import it.polimi.ingsw.gc31.model.PlayerColor;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;
import it.polimi.ingsw.gc31.view.client.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.UUID;

public interface GameServer extends Remote {
    void createGame() throws RemoteException, NoResourceMatch;
    Map<UUID, GameInstance> getGames() throws RemoteException;
    GameInstance getGame(UUID instanceID) throws RemoteException;
    GameInstance join(String playerName, PlayerColor color) throws RemoteException, NoResourceMatch;
    void register(Client client) throws RemoteException;
    void leave(UUID playerID) throws RemoteException;

    void getData() throws RemoteException;
}
