package it.polimi.ingsw.gc31.messages;

import java.util.Map;

public interface Message {

    /**
     * Setter for "clientMessageType" attribute.
     * @param payLoad: Map with a String as Key and a String as Value.
     */
    void setPayLoad(Map<String, String> payLoad);

    /**
     * Getter for "payload" attribute.
     * @return Map with a String as Key and a String as Value.
     */
    Map<String, String> getPayload();
}
