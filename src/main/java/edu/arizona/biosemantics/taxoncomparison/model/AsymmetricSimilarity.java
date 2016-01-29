package edu.arizona.biosemantics.taxoncomparison.model;

public class AsymmetricSimilarity<T> {
	
	private T itemA;
	private T itemB;
	private double similarity = 0f;
	private double oppositeSimilarity = 0f;
		
	public AsymmetricSimilarity(T itemA, T itemB, double similarity, double oppSimilarity) {
		super();
		this.similarity = similarity;
		this.oppositeSimilarity = oppSimilarity;
	}
	
	public double getSimilarity() {
		return similarity;
	}

	public double getOppositeSimilarity() {
		return oppositeSimilarity;
	}
	
	public T getItemA() {
		return itemA;
	}
	
	public T getItemB() {
		return itemB;
	}
}
