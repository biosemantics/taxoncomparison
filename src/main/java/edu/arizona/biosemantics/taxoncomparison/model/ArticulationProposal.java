package edu.arizona.biosemantics.taxoncomparison.model;

public class ArticulationProposal {
	RCC5Relation rcc5Relation = null; //make it enum
	double confidence = Double.NaN;

	
	@Override
	public String toString(){
		return this.rcc5Relation+" ["+(Double.isNaN(confidence)? "confidence cannot be assessed": confidence)+"]";
	}
	
	public RCC5Relation getRcc5Relation() {
		return rcc5Relation;
	}
	public void setRcc5Relation(RCC5Relation rcc5Relation) {
		this.rcc5Relation = rcc5Relation;
	}
	public double getConfidence() {
		return confidence;
	}
	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

}
