package edu.arizona.biosemantics.taxoncomparison.model;

import edu.arizona.biosemantics.euler.alignment.shared.model.Relation;

public class RelationProposal {
	
	private Relation relation;
	private double confidence;
	private double similarity;
	private double oppositeSimilarity;
		
	public RelationProposal(Relation relation, double confidence,
			double similarity, double oppositeSimilarity) {
		super();
		this.relation = relation;
		this.confidence = confidence;
		this.similarity = similarity;
		this.oppositeSimilarity = oppositeSimilarity;
	}

	public Relation getRelation() {
		return relation;
	}

	public double getConfidence() {
		return confidence;
	}

	public double getSimilarity() {
		return similarity;
	}

	public double getOppositeSimilarity() {
		return oppositeSimilarity;
	}

	@Override
	public String toString() {
		return relation.getDisplayName() + " (" + confidence + ") " + "similarity: " + similarity + "; oppositeSimilarity: " + oppositeSimilarity;
	}
	
}