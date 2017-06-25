package it.polimi.ingsw.gc31.messages;

public class BasicMessage implements Message{
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
