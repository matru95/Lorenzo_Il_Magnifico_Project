package it.polimi.ingsw.gc31.model;

public class FaithTile {
	private int age;
	private FaithEffect effect;
	
	public FaithTile(int age) {
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
}
