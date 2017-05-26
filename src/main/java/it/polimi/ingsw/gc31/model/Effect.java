package it.polimi.ingsw.gc31.model;

public class Effect extends Transaction{
	
	public Effect(int wood, int gold, int stone, int servant, int warPoints) {
		super(wood, gold, stone, servant, warPoints);
	}
	
	private Boolean isApplicable() {
		//TODO
		return true;
	}
	
	public void runEffect() {
		if(this.isApplicable()) {
			// run effect
		} else {
			return;
		}
		
	}

}
