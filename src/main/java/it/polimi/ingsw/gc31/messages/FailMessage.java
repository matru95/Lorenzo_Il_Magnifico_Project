package it.polimi.ingsw.gc31.messages;


public class FailMessage implements Message{
    private BasicMessage basicMessage;
    private FailType failType;
    private String message;

    public FailMessage(BasicMessage basicMessage, FailType failType) {
        this.basicMessage = basicMessage;
        this.failType = failType;
    }

    @Override
    public void setRequestType(RequestType requestType) {
        basicMessage.setRequestType(requestType);
    }

    @Override
    public RequestType getRequestType() {
        return basicMessage.getRequestType();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
