package it.polimi.ingsw.GC_31.model;

public class Card {
	private final String cardName;
	private final int cardID;
	private final int cardAge;
	private final int warPointsBond;

	private Boolean deck;
	private Effect normalEffect;
	private Effect instantEffect;
	private CardColor color;
	
	public Card(String cardName, int cardID, int cardAge, int warPointsBond, Effect normalEffect, 
			Effect instantEffect) {

		this.cardName = cardName;
		this.cardID = cardID;
		this.cardAge = cardAge;
		this.warPointsBond = warPointsBond;
		this.normalEffect = normalEffect;
		this.instantEffect = instantEffect;
//		A card first starts in the deck;
		this.deck = true;
	}
	
	public void execEffect(Effect effect) {
		effect.runEffect();
	}
	
	

}
