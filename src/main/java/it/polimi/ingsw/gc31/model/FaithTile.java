package it.polimi.ingsw.gc31.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.model.resources.Resource;
import java.util.ArrayList;
import java.util.List;

public class FaithTile {
	private int age;
	private int id;
	private FaithEffect faithEffect;
	private List<Resource> gainFewerStack;
	private int harvestFewer=0;
	private int productionFewer=0;
	private int diceFewer=0;
	private CardColor fewerDiceCardColor;
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
		this.faithEffect = new FaithEffect();
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
			return "";
		}
	}
	public int getAge() {
		return age;
	}
	public int getId() { return this.id;}
	public FaithEffect getFaithEffect() {
		return faithEffect;
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
	public void setProductionFewer(int productionFewer) {
		this.productionFewer = productionFewer;
	}
	public void setDiceFewer(int diceFewer) {
		this.diceFewer = diceFewer;
	}
	public void setFewerDiceCardColor(CardColor fewerDiceCardColor) {
		this.fewerDiceCardColor = fewerDiceCardColor;
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
		String description= new String();
		ObjectNode effect=mapper.createObjectNode();

		//TODO TOJSON CON LA SCRITTA DEL DETERMINATO EFFETTO GIA FORMATA
		/*
		if(!(this.gainFewerStack.isEmpty())){

			ObjectNode gainFewer= mapper.createObjectNode();

			for (Resource resource :this.gainFewerStack){
				gainFewer.put(resource.getResourceName().toString().toLowerCase(),resource.getNumOf());
				description+="You will get("+resource.getNumOf()+") less "+resource.getResourceName()+" for each faithEffect that comes from an action space or from one development card. ";
			}
			effect.set("gainFewer",gainFewer);
			effect.put("description",description);
			faithObjectNode.set("faithEffect",effect);
		}*/

		if(this.harvestFewer!=0){


			effect.put("harvestFewer",this.harvestFewer);
			faithObjectNode.set("faithEffect",effect);

			description+="Harvest's dice value now will be decrease by "+this.harvestFewer+" points.\n";
            faithObjectNode.put("description"," " + description);
		}

		if(this.productionFewer!=0){

			effect.put("productionFewer",this.productionFewer);
			faithObjectNode.set("faithEffect",effect);

			description += "Production's dice value now will be decrease by "+ this.productionFewer+ " points.";
            faithObjectNode.put("description"," " + description);
		}

		if(this.diceFewer!=0){

			effect.put("diceFewer",this.diceFewer);
			faithObjectNode.set("faithEffect",effect);

			description+="All your colored Family Members receive a -" + this.diceFewer + " reduction of their value each time you place them.";
            faithObjectNode.put("description"," " + description);

		}

		if(this.fewerDiceCardColor !=null){
			ObjectNode fewerDiceCard= mapper.createObjectNode();
			fewerDiceCard.put("cardColor",this.fewerDiceCardColor.toString());
			fewerDiceCard.put("diceValue",this.fewerDiceCardValue);

			description+="Each time you take a"+ this.fewerDiceCardColor.toString() +" Card your action receives a -"+this.fewerDiceCardValue+" reduction of its value.";
            faithObjectNode.put("description"," " + description);

			effect.set("fewerDiceCard",fewerDiceCard);
			faithObjectNode.set("faithEffect",effect);
		}

		if(this.noMarket){
			effect.put("noMarket",true);
			description+="You can’t place your Family Members in the Market action spaces.";
            faithObjectNode.put("description"," " + description);
			faithObjectNode.set("faithEffect",effect);
		}

		if(this.doubleServants){
			description+="You have to spend 2 servants to increase your action value by 1";
			faithObjectNode.put("description"," " + description);

			effect.put("doubleServants",true);
			faithObjectNode.set("faithEffect",effect);

		}

		if(this.skipFirstRound){
			effect.put("noSkipFirstRound",true);
			description+="Each round, you skip your first turn. You start taking actions from the second turn. When all players have taken all their turns, you may still place your last Family Member.";

            faithObjectNode.put("description"," " + description);
			faithObjectNode.set("faithEffect",effect);
		}

		if(this.noEndGamePointsCardColor!=null){
			effect.put("cardColor",this.noEndGamePointsCardColor.toString());

			description+="No more end game effects for"+this.noEndGamePointsCardColor.toString()+" card";
            faithObjectNode.put("description"," " + description);
			faithObjectNode.set("faithEffect",effect);
		}

		if(this.loseForEveryResource){
			effect.put("loseForEveryResource",this.skipFirstRound);
			description+="At the end of the game, you lose 1 Victory Point for every resource (wood, stone, coin, servant) in your supply on your Personal Board.";
            faithObjectNode.put("description"," " + description);
			faithObjectNode.set("faithEffect",effect);
		}

		if(!(this.forEveryRes.isEmpty())){
			description+="At the end of the game, ";
			ObjectNode loseForEvery = mapper.createObjectNode();
			ObjectNode loseForEveryFor = mapper.createObjectNode();
			ObjectNode lost = mapper.createObjectNode();
			for(Resource resource: this.loseRes){
				lost.put(resource.getResourceName().toString(),resource.getNumOf());
				description+="you lose"+resource.getNumOf()+" "+resource.getResourceName();
			}
			loseForEvery.set("lost",lost);

			for(Resource resource : this.forEveryRes){
				loseForEveryFor.put(resource.getResourceName().toString().toLowerCase(),resource.getNumOf());
				description+=" for every"+resource.getResourceName()+ " you have.";
			}
			loseForEvery.set("for",loseForEveryFor);

			effect.set("loseForEvery",loseForEvery);

            faithObjectNode.put("description"," " + description);
			faithObjectNode.set("faithEffect",effect);
		}

		if(this.loseForEveryCostCardColor!=null){

			ObjectNode loseForEveryCost = mapper.createObjectNode();
			loseForEveryCost.put("cardColor",loseForEveryCostCardColor.toString().toLowerCase());
			description+="At the end of the game, you lose 1 Victory Point for every ";
			ObjectNode cardTypeCost = mapper.createObjectNode();
			for(Resource resource: this.loseForEveryCost){
				description+=resource.getResourceName().toString()+" ";
				cardTypeCost.put(resource.getResourceName().toString(), resource.getNumOf());
			}
			loseForEveryCost.set("cardTypeCost", cardTypeCost);
			description+=" on your "+ this.loseForEveryCostCardColor +" card";

			effect.set("loseForEveryCost",loseForEveryCost);
			faithObjectNode.set("faithEffect",effect);
            faithObjectNode.put("description"," " + description);
		}
		return faithObjectNode;
	}
}
