package it.polimi.ingsw.gc31.messages;

import java.util.Map;

public interface Message {
    void setPayLoad(Map<String, String> payLoad);
    Map<String, String> getPayload();
}
