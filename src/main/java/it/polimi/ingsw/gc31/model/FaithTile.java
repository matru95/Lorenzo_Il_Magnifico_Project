package it.polimi.ingsw.gc31.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.org.apache.regexp.internal.RE;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.model.resources.Resource;
import java.util.ArrayList;
import java.util.List;

public class FaithTile {
	private int age;
	private int id;
	private FaithEffect effect;
	private List<Resource> gainFewerStack;
	private int harvestFewer=0;
	private int productionFewer=0;
	private int diceFewer=0;
	private CardColor fewerdicecardcolor;
	private int fewerDiceCardValue=0;
	private boolean noMarket=false;
	private boolean doubleServants=false;
	private boolean skipFirstRound=false;
	private CardColor noEndGamePointsCardColor;
	private List <Resource> forEveryRes=new ArrayList<>();
	private List <Resource> loseRes=new ArrayList<>();
	private CardColor loseForEveryCostCardColor;
	private List <Resource> loseForEveryCost;
	private boolean loseForEveryResource;
	public FaithTile(int id, int age) {
		this.id=id;
		this.age = age;
		this.effect = new FaithEffect();
	}

	public void execute(Player player) {
		//TODO ???
		return;
	}
	public void createFaithEffect(){
		//TODO inserire i relativi valori nella class
	}
	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode faithObjectNode = toJson();
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(faithObjectNode);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	public int getAge() {
		return age;
	}
	public int getId() { return this.id;}
	public FaithEffect getEffect() {
		return effect;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setGainFewerStack(List<Resource> gainFewerStack) {
		this.gainFewerStack = gainFewerStack;
	}
	public void setHarvestFewer(int harvestFewer) {
		this.harvestFewer = harvestFewer;
	}
	public void setProfuctionFewer(int productionFewer) {
		this.productionFewer = productionFewer;
	}
	public void setDiceFewer(int diceFewer) {
		this.diceFewer = diceFewer;
	}
	public void setFewerdicecardcolor(CardColor fewerdicecardcolor) {
		this.fewerdicecardcolor = fewerdicecardcolor;
	}
	public void setFewerDiceCardValue(int fewerDiceCardValue) {
		this.fewerDiceCardValue = fewerDiceCardValue;
	}
	public void setNoMarket(boolean noMarket) {
		this.noMarket = noMarket;
	}
	public void setDoubleServants(boolean doubleServants) {
		this.doubleServants = doubleServants;
	}
	public void setSkipFirstRound(boolean skipFirstRound) {
		this.skipFirstRound = skipFirstRound;
	}
	public void setNoEndGamePointsCardColor(CardColor noEndGamePointsCardColor) {
		this.noEndGamePointsCardColor = noEndGamePointsCardColor;
	}
	public void setForEveryRes(List<Resource> forEveryRes) {
		this.forEveryRes = forEveryRes;
	}
	public void setLoseRes(List<Resource> loseRes) {
		this.loseRes = loseRes;
	}
	public void setLoseForEveryCostCardColor(CardColor loseForEveryCostCardColor) {
		this.loseForEveryCostCardColor = loseForEveryCostCardColor;
	}
	public void setLoseForEveryCost(List<Resource> loseForEveryCost) {
		this.loseForEveryCost = loseForEveryCost;
	}
	public void setLoseForEveryResource(boolean loseForEveryResource) {
		this.loseForEveryResource = loseForEveryResource;
	}
	public ObjectNode toJson() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode faithObjectNode = mapper.createObjectNode();
		faithObjectNode.put("id", this.id);
		faithObjectNode.put("age", this.age);
		//TODO TOJSON CON LA SCRITTA DEL DETERMINATO EFFETTO GIA FORMATA
		if(!(this.gainFewerStack.isEmpty())){
			ArrayNode gainFewerStack= mapper.createArrayNode();
			ObjectNode gainFewerRes= mapper.createObjectNode();
			for (Resource resource :this.gainFewerStack){
				gainFewerRes.put(resource.getResourceName().toString(),resource.getNumOf());
				gainFewerStack.add(gainFewerRes);
			}
			faithObjectNode.set("gainFewer",gainFewerStack);
		}
		if(this.harvestFewer!=0){
			ObjectNode harvestFewer= mapper.createObjectNode();
			harvestFewer.put("harvestFewer",this.harvestFewer);
			faithObjectNode.set("harvestFewer",harvestFewer);
		}
		if(this.productionFewer!=0){
			ObjectNode productionFewer= mapper.createObjectNode();
			productionFewer.put("productionFewer",this.productionFewer);
			faithObjectNode.set("productionFewer",productionFewer);
		}
		if(this.diceFewer!=0){
			ObjectNode diceFewer= mapper.createObjectNode();
			diceFewer.put("diceFewer",this.diceFewer);
			faithObjectNode.set("diceFewer",diceFewer);
		}
		if(this.fewerdicecardcolor!=null){
			ObjectNode fewerdicecard= mapper.createObjectNode();
			fewerdicecard.put("cardColor",this.fewerdicecardcolor.toString());
			fewerdicecard.put("diceValue",this.fewerDiceCardValue);
			faithObjectNode.set("fewerDiceCard",fewerdicecard);
		}
		if(this.noMarket){
			faithObjectNode.put("noMarket",true);
		}
		if(this.doubleServants){
			faithObjectNode.put("doubleServants",true);
		}
		if(this.skipFirstRound){
			faithObjectNode.put("noSkipFirstRound",true);
		}
		if(this.noEndGamePointsCardColor!=null){
			ObjectNode noEndGamePoints= mapper.createObjectNode();
			noEndGamePoints.put("cardColor",this.noEndGamePointsCardColor.toString());
		}
		if(this.loseForEveryResource){
			faithObjectNode.put("loseForEveryResource",this.skipFirstRound);
		}
		if(!(this.forEveryRes.isEmpty())){
			ArrayNode loseForEvery= mapper.createArrayNode();
			ObjectNode loseForEveryFor= mapper.createObjectNode();
			for(Resource resource : forEveryRes){
				loseForEveryFor.put(resource.getResourceName().toString(),resource.getNumOf());
			}
			loseForEvery.add(loseForEveryFor);
			ObjectNode lost=mapper.createObjectNode();
			for(Resource resource: this.loseRes){
				lost.put(resource.getResourceName().toString(),resource.getNumOf());
			}
			loseForEvery.add(lost);
		}
		if(this.loseForEveryCostCardColor!=null){
			ObjectNode loseForEveryCost= mapper.createObjectNode();
			ObjectNode loseForEveryCardColor= mapper.createObjectNode();
			loseForEveryCardColor.put("cardColor",loseForEveryCostCardColor.toString());
			loseForEveryCost.set("loseForEveryCost",loseForEveryCardColor);

			ObjectNode cardTypeCost= mapper.createObjectNode();
			for(Resource resource: this.loseForEveryCost){
				cardTypeCost.put(resource.getResourceName().toString(), resource.getNumOf());
			}
			loseForEveryCost.set("loseForEveryCost", cardTypeCost);
		}
		return faithObjectNode;
	}
}
