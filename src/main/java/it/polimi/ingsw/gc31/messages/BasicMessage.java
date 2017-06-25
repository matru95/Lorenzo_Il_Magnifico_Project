package it.polimi.ingsw.gc31.messages;

import java.io.Serializable;

public class BasicMessage implements Message, Serializable{
    private RequestType requestType;

    public BasicMessage(RequestType requestType) {
        this.requestType = requestType;
    }

    public BasicMessage() {
    }

    @Override
    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    @Override
    public RequestType getRequestType() {
        return requestType;
    }

    @Override
    public String getMessage() {
        return requestType.toString();
    }
}
