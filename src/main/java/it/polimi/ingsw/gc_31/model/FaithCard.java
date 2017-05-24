package it.polimi.ingsw.gc_31.model;

public class FaithCard {
	private int age;
	private FaithEffect effect;
	
	public FaithCard(int age) {
		this.age = age;
		this.effect = new FaithEffect();
	}
	
	public void execute() {
		return;
	}
}
