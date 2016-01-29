package edu.arizona.biosemantics.taxoncomparison.model;

public class CharacterState {

	public String organ;
	public String character;
	public String state;
	public int weight;
	
	@Override
	public String toString() {
		return organ + " " + character + " " + state + " (" + weight + ")";
	}
}
