package it.polimi.ingsw.gc31.messages;

public class ActionMessage implements Message{
    private BasicMessage basicMessage;
    private ActionType actionType;
    private String JSONMessage;
    private String gameID;

    public ActionMessage(BasicMessage basicMessage, ActionType actionType) {
        this.basicMessage = basicMessage;
        this.actionType = actionType;
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
        return JSONMessage;
    }

    public void setMessage(String JSONMessage) {
        this.JSONMessage = JSONMessage;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public String getGameID() {
        return gameID;
    }

}
