package edu.arizona.biosemantics.taxoncomparison.model;

import java.util.Collection;

public class ArticulationProposal {

	private CharacterizedTaxon characterizedTaxonA;
	private CharacterizedTaxon characterizedTaxonB;
	private Collection<RelationProposal> relationProposals;
	
	public ArticulationProposal(CharacterizedTaxon characterizedTaxonA, CharacterizedTaxon characterizedTaxonB, Collection<RelationProposal> relationProposals) {
		this.characterizedTaxonA = characterizedTaxonA;
		this.characterizedTaxonB = characterizedTaxonB;
		this.relationProposals = relationProposals;
	}
		
	public CharacterizedTaxon getCharacterizedTaxonA() {
		return characterizedTaxonA;
	}

	public CharacterizedTaxon getCharacterizedTaxonB() {
		return characterizedTaxonB;
	}

	public Collection<RelationProposal> getRelationProposals() {
		return relationProposals;
	}

	public String toString() {
		return characterizedTaxonA.getName() + " " + characterizedTaxonB.getName() + " " + relationProposals;
	}
	
}
