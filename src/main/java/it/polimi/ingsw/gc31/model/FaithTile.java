package it.polimi.ingsw.gc31.model;

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
	private int profuctionFewer=0;
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
	private boolean loseForEveryResource;



	public FaithTile(int id, int age) {
		this.id=id;
		this.age = age;
		this.effect = new FaithEffect();
	}
	
	public void execute() {
		return;
	}

    public int getAge() {
        return age;
    }

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

	public void setProfuctionFewer(int profuctionFewer) {
		this.profuctionFewer = profuctionFewer;
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

	public void setLoseForEveryResource(boolean loseForEveryResource) {
		this.loseForEveryResource = loseForEveryResource;
	}
}
