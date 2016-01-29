package edu.arizona.biosemantics.taxoncomparison.model;

import edu.arizona.biosemantics.euler.alignment.shared.model.Relation;

public class RelationProposal {
	
	public Relation relation;
	public double confidence;
	public double similarity;
	public double oppositeSimilarity;
	
	@Override
	public String toString() {
		return relation.getDisplayName() + " (" + confidence + ") " + "similarity: " + similarity + "; oppositeSimilarity: " + oppositeSimilarity;
	}
	
}