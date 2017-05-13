package it.polimi.ingsw.GC_31;

public abstract class Card {
	private final String cardName;
	private final int cardID;
	private final int cardAge;
	private final int warPointsBond;

	private Boolean deck;
	private Effect normalEffect;
	private Effect instantEffect;
	
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
	
	public execEffect(Effect effect) {
		effect.runEffect();
	}
	
	

}
