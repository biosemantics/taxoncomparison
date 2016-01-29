package edu.arizona.biosemantics.taxoncomparison.model;

import java.util.Collection;

public class ArticulationProposal {

	public CharacterizedTaxon characterizedTaxonA;
	public CharacterizedTaxon characterizedTaxonB;
	private Collection<RelationProposal> relationProposals;
	
	public ArticulationProposal(CharacterizedTaxon characterizedTaxonA, CharacterizedTaxon characterizedTaxonB, Collection<RelationProposal> relationProposals) {
		this.characterizedTaxonA = characterizedTaxonA;
		this.characterizedTaxonB = characterizedTaxonB;
		this.relationProposals = relationProposals;
	}
	
	public String toString() {
		return characterizedTaxonA.getName() + " " + characterizedTaxonB.getName() + " " + relationProposals;
	}
	
}
