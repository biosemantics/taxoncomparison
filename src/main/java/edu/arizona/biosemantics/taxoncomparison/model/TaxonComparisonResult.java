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
	private float overlapToleranceMin;
	private float overlapToleranceMax;
	private float inclusionToleranceMin;
	private float disjointToleranceMax;
	private float congruenceToleranceMin;

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
	
	
	public float getOverlapToleranceMin() {
		return overlapToleranceMin;
	}


	public void setOverlapToleranceMin(float overlapToleranceMin) {
		this.overlapToleranceMin = overlapToleranceMin;
	}


	public float getOverlapToleranceMax() {
		return overlapToleranceMax;
	}


	public void setOverlapToleranceMax(float overlapToleranceMax) {
		this.overlapToleranceMax = overlapToleranceMax;
	}


	public float getInclusionToleranceMin() {
		return inclusionToleranceMin;
	}


	public void setInclusionToleranceMin(float inclusionToleranceMin) {
		this.inclusionToleranceMin = inclusionToleranceMin;
	}


	public float getDisjointToleranceMax() {
		return disjointToleranceMax;
	}


	public void setDisjointToleranceMax(float disjointToleranceMax) {
		this.disjointToleranceMax = disjointToleranceMax;
	}


	public float getCongruenceToleranceMin() {
		return congruenceToleranceMin;
	}


	public void setCongruenceToleranceMin(float congruenceToleranceMin) {
		this.congruenceToleranceMin = congruenceToleranceMin;
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
