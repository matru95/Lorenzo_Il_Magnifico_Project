package it.polimi.ingsw.gc31.model.cards;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc31.model.effects.Effect;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class CardParser {
    private CardColor cardColor;
    private String cardName;
    private int cardID;
    private int cardAge;
    private int warPointsBond;
    private Map<String, Resource>[] cost;
    private Effect[] normalEffect;
    private Effect[] instantEffect;
    private Boolean isOnDeck;

    public CardParser(){
    }
    public static void main(String[] args) {
        ObjectMapper mapper =new ObjectMapper();
        try{
            File jsonInputFile=new File("src/config/Card.json");
            //leggo tutto il card.json
            JsonNode rootNode=mapper.readTree(jsonInputFile);

            JsonNode cardAge=rootNode.path("cardage");
            JsonNode title=rootNode.path("title");
            JsonNode id=rootNode.path("id");
            JsonNode cardcolor=rootNode.path("cardcolor");

            JsonNode activationvalue = null;
            if(rootNode.has("activationvalue")){
                activationvalue = rootNode.path("activationvalue");
            }

            //Creando il costo/i costi della carta
            JsonNode cost1=rootNode.path("cost");
            Iterator<JsonNode> iterator= cost1.elements();
            while (iterator.hasNext()){
                JsonNode costi= iterator.next();
                JsonNode costo;
                System.out.println("Costo:");
                for (CostName resource : CostName.values()){
                    String risorsa=resource.toString().toLowerCase();
                    if (costi.has(risorsa)){
                        costo=costi.path(risorsa);
                        System.out.println(risorsa+" = "+costo.asInt());
                    }
                }
            }
            JsonNode instanteffect = null;
            JsonNode addresource=null;
            if(rootNode.has("instanteffect"))
            {
                instanteffect=rootNode.path("instanteffect");
                Iterator <JsonNode> instanteffects = instanteffect.elements();
                while(instanteffects.hasNext()) {
                    JsonNode effetto = instanteffects.next();
                    String aneffect = effetto.asText();
                    if (aneffect.equalsIgnoreCase("addresource")) {
                        addresource = instanteffect.path("addresource");
                        Iterator<JsonNode> iterator2 = addresource.elements();
                        while (iterator2.hasNext()) {
                            System.out.println(iterator2 + " = ");
                        }
                    }
                }
            }



            System.out.println("Age = " + cardAge.asInt());
            System.out.println("Title = " +title.asText());
            System.out.println("Id = " + id.asInt());
            System.out.println("Card Color = " + cardcolor.asText());
            System.out.println("activatiovalue = " + activationvalue.asInt());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
