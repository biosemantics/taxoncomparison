/**
 * 
 */
package edu.arizona.biosemantics.taxoncomparison.model;

import java.util.ArrayList;

import edu.arizona.biosemantics.taxoncomparison.model.ArticulationProposal;

/**
 * @author Hong Cui
 *
 */
public class TaxonComparisonResult {
	ArrayList<ArticulationProposal> aps = new ArrayList<ArticulationProposal>();
	AsymmetricSimilarityScore similiarityScore = null;
	
	private float disjointSimMax;
	private float symDiffMax;
	private float congruenceSimMin;
	private float inclusionSimMin;
	private float asymDiffMax;
	
	TaxonConcept referenceConcept = null;
	TaxonConcept comparisonConcept = null;
	

	/**
	 * 
	 */
	public TaxonComparisonResult() {
		// TODO Auto-generated constructor stub
	}


	public ArrayList<ArticulationProposal> getArticulationProposals() {
		return aps;
	}

	public void setArticulationProposals(ArrayList<ArticulationProposal> aps) {
		this.aps = aps;
	}
	
	public void addArticulationProposal(ArticulationProposal ap) {
		this.aps.add(ap);
	}

	public void setSimiliarityScore(AsymmetricSimilarityScore similiarityScore) {
		this.similiarityScore = similiarityScore;
	}


	public AsymmetricSimilarityScore getSimiliarityScore() {
		return this.similiarityScore;
	}
	

	public float getDisjointSimMax() {
		return disjointSimMax;
	}


	public void setDisjointSimMax(float disjointSimMax) {
		this.disjointSimMax = disjointSimMax;
	}


	public float getSymDiffMax() {
		return symDiffMax;
	}


	public void setSymDiffMax(float symDiffMax) {
		this.symDiffMax = symDiffMax;
	}


	public float getCongruenceSimMin() {
		return congruenceSimMin;
	}


	public void setCongruenceSimMin(float congruenceSimMin) {
		this.congruenceSimMin = congruenceSimMin;
	}


	public float getInclusionSimMin() {
		return inclusionSimMin;
	}


	public void setInclusionSimMin(float inclusionSimMin) {
		this.inclusionSimMin = inclusionSimMin;
	}


	public float getAsymDiffMax() {
		return asymDiffMax;
	}


	public void setAsymDiffMax(float asymDiffMax) {
		this.asymDiffMax = asymDiffMax;
	}

	public TaxonConcept getReferenceConcept() {
		return referenceConcept;
	}


	public void setReferenceConcept(TaxonConcept referenceConcept) {
		this.referenceConcept = referenceConcept;
	}


	public TaxonConcept getComparisonConcept() {
		return comparisonConcept;
	}


	public void setComparisonConcept(TaxonConcept comparisonConcept) {
		this.comparisonConcept = comparisonConcept;
	}


	@Override
	public String toString(){
		String string = "similarity = "+ this.getSimiliarityScore().getSimilarity()+";";
		string += "oppSimilarity = "+ this.getSimiliarityScore().getOppSimilarity()+";";
		string += "articulation = ";
		for(ArticulationProposal ap: aps){
			string += ap.toString()+" ";
		}
		return string;
	}

}
