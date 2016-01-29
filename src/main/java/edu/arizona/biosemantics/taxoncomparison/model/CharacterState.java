package edu.arizona.biosemantics.taxoncomparison.model;

public class CharacterState {

	private String organ;
	private String character;
	private String state;
	private int weight;
		
	public CharacterState(String organ, String character, String state,	int weight) {
		super();
		this.organ = organ;
		this.character = character;
		this.state = state;
		this.weight = weight;
	}


	public String getOrgan() {
		return organ;
	}

	public String getCharacter() {
		return character;
	}

	public String getState() {
		return state;
	}

	public int getWeight() {
		return weight;
	}

	@Override
	public String toString() {
		return organ + " " + character + " " + state + " (" + weight + ")";
	}
}
