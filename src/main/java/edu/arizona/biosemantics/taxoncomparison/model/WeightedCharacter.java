/**
 * 
 */
package edu.arizona.biosemantics.taxoncomparison.model;

import edu.arizona.biosemantics.matrixreview.shared.model.core.Character;

/**
 * @author Hong Cui
 *
 */
public class WeightedCharacter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	boolean weighted = false; //if weighted is set to false, ignore the weight
	Float weight = null; 
	Character character = null;
	

	/**
	 * 
	 */
	public WeightedCharacter() {
		super();
	}

	public WeightedCharacter(Float weight, Character character){
		if(weight!=null && ! weight.isNaN()){
			this.weight = weight;
			this.weighted = true;
		}
		this.character = character;
	}
	
	public boolean isWeighted() {
		return weighted;
	}

	public void setWeighted(boolean weighted) {
		this.weighted = weighted;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}
	
	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}
	
	@Override
	public String toString(){
		return this.character.toString()+" ["+ (weight==null||Float.isNaN(weight)? "unweighted" : weight)+"]";
	}

}
