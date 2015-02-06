/**
 * 
 */
package edu.arizona.biosemantics.taxoncomparison.model;

/**
 * @author Hong Cui
 *
 */
public class AsymmetricSimilarityScore {
	float similarity = 0f;
	float oppSimilarity = 0f;
	/**
	 * 
	 */
	public AsymmetricSimilarityScore() {
		// TODO Auto-generated constructor stub
	}

	
	public void setSimilarity(float similarity) {
		this.similarity = similarity;
		
	}

	public void setOppSimilarity(float similarity) {
		this.oppSimilarity = similarity;
		
	}

	public float getSimilarity() {
		return similarity;
	}

	public float getOppSimilarity() {
		return oppSimilarity;
	}
	



}
