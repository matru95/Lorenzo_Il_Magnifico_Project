package it.polimi.ingsw.gc31.messages;

public enum ServerMessageEnum {
    MOVEREQUEST, UPDATE, PARCHMENTREQUEST, EXCOMMUNICATIONREQUEST, EXCHANGEREQUEST, COSTREQUEST, MOVEMENTFAIL, FREECARDREQUEST, REGISTERSUCCESS, ENDGAME, TIMEOUT
}
//EXCOMMUNICATION no payload input, return mappa con un solo valore("applyExcommunication", String -> "YES"/"NO"