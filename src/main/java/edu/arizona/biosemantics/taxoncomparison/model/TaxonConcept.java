/**
 * 
 */
package edu.arizona.biosemantics.taxoncomparison.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.arizona.biosemantics.matrixreview.shared.model.core.Taxon;

/**
 * @author Hong Cui
 * taxon concept including its lower taxon concepts
 */
public class TaxonConcept {

	/**
	 * 
	 */
	Taxon taxon = null;
	ArrayList<OrganCharacterState> characters = new ArrayList<OrganCharacterState> ();
	ArrayList<TaxonConcept> children = new ArrayList<TaxonConcept>();
	
	
	public TaxonConcept() {
	}

	public Taxon getTaxon() {
		return taxon;
	}

	public void setTaxon(Taxon taxon) {
		this.taxon = taxon;
	}

	public ArrayList<OrganCharacterState> getCharacters() {
		return characters;
	}

	public void setCharacters(ArrayList<OrganCharacterState> characters) {
		this.characters = characters;
	}

	public void addCharacters(ArrayList<OrganCharacterState> characters) {
		this.characters.addAll(characters);
	}
	
	public void addCharacter(OrganCharacterState character) {
		this.characters.add(character);
	}
	
	public void addChildTaxon(TaxonConcept child){
		children.add(child);
	}
	
	public void addChildrenTaxa(List<TaxonConcept> children){
		children.addAll(children);
	}
	
	public ArrayList<TaxonConcept> getChildrenConcepts(){
		return this.children;
	}
	
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer("Taxon Concept: "+ this.taxon.toString()+System.getProperty("line.separator")
				+"with characters: "+System.getProperty("line.separator"));
		for(OrganCharacterState ocs: characters){
			sb.append(ocs.toString());
			sb.append(System.getProperty("line.separator"));
		}
		
		if(! children.isEmpty()){
			sb.append("with children: "+System.getProperty("line.separator"));
			for(TaxonConcept c: children){
				sb.append(c.toString());
				sb.append(System.getProperty("line.separator"));
			}
		}
		return sb.toString();
	}
}
