package it.polimi.ingsw.gc31.model.cards;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc31.model.effects.Effect;
import it.polimi.ingsw.gc31.model.resources.Resource;
import it.polimi.ingsw.gc31.model.resources.ResourceName;

import java.io.File;
import java.io.IOException;
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

    public CardParser() {

    }

    public static void main(String[] args) {
        ObjectMapper mapper =new ObjectMapper();
        try{
            File jsonInputFile=new File("/home/leonardo/IdeaProjects/Lorenzo_Il_Magnifico_Project_Di_Prova/src/config/Card.json");
            JsonNode rootNode=mapper.readTree(jsonInputFile);

            JsonNode cardAge=rootNode.path("cardage");
            JsonNode title=rootNode.path("title");
            JsonNode id=rootNode.path("id");
            JsonNode cardcolor=rootNode.path("cardcolor");
            JsonNode harvestvalue = null;

            if(rootNode.has("harvestvalue")){
                harvestvalue = rootNode.path("harvestvalue");
            }
            JsonNode productionvalue=null;
            if(rootNode.has("productionvalue")){
                productionvalue = rootNode.path("productionvalue");

            }
            JsonNode instanteffect=null;
            JsonNode addresource[];
         /*   if(rootNode.has("instanteffect")){
                instanteffect=rootNode.path("instanteffect");
                if(instanteffect.has("addresource")){
                    instanteffect=rootNode.path("addresource");
                    for(enum Resource : it.polimi.ingsw.gc31.model.cards.Resource) {
                        addresource[Resource]=instanteffect.path("Stone");
                    }
                }
            }*/
        System.out.println("Age = " + cardAge.asInt());
        System.out.println("Title = " +title.asText());
        System.out.println("Id = " + id.asInt());
        System.out.println("Card Color = " + cardcolor.asText());
        System.out.println("harvestvalue = " + harvestvalue.asInt());
        System.out.println("productionvalue = " + productionvalue.asInt());
        //System.out.println("Instanteffect-> addresource = " +addresource.asInt() );
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
