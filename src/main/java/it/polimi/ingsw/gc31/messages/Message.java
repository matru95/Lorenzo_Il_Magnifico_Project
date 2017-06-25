package it.polimi.ingsw.gc31.messages;

public interface Message {
    void setRequestType(RequestType requestType);
    RequestType getRequestType();
    String getMessage();
}
