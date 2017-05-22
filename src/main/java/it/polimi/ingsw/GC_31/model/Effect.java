package it.polimi.ingsw.GC_31.model;

public class Effect extends Transaction{
	
	public Effect(int wood, int gold, int stone, int servant, int warPoints) {
		super(wood, gold, stone, servant, warPoints);
	}
	
	private Boolean isApplicable() {
		
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
