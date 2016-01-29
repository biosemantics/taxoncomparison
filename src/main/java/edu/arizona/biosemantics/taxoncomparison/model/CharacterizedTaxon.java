package edu.arizona.biosemantics.taxoncomparison.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import edu.arizona.biosemantics.common.taxonomy.Taxon;
import edu.arizona.biosemantics.common.taxonomy.TaxonIdentification;

public class CharacterizedTaxon implements Serializable {

	private String name;
	private TaxonIdentification taxonIdentification;
	private CharacterizedTaxon parent;
	private List<CharacterizedTaxon> children = new LinkedList<CharacterizedTaxon>();
	private Collection<CharacterState> characterStates;
	
	public CharacterizedTaxon(String name, TaxonIdentification taxonIdentification, Collection<CharacterState> characterStates) {
		this.name = name;
		this.taxonIdentification = taxonIdentification;
		this.characterStates = characterStates;
	}
	
	public List<CharacterizedTaxon> getChildren() {
		return children;
	}
	
	public void addChild(CharacterizedTaxon taxon) {
		taxon.setParent(this);
		children.add(taxon);
	}
	
	public void removeChild(Taxon taxon) {
		taxon.setParent(null);
		children.remove(taxon);
	}
	
	public void addChild(int index, CharacterizedTaxon child) {
		child.setParent(this);
		children.add(index, child);
	}

	public void setParent(CharacterizedTaxon parent) {
		this.parent = parent;
	}
	
	public CharacterizedTaxon getParent() {
		return parent;
	}

	public TaxonIdentification getTaxonIdentification() {
		return taxonIdentification;
	}

	public String getName() {
		return name;
	}

	public Collection<CharacterState> getCharacterStates() {
		return this.characterStates;
	}

	
	
}
