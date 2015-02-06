package edu.arizona.biosemantics.taxoncomparison.model;

import java.util.ArrayList;
import java.util.Hashtable;

public class ScoreMatrix {
	Hashtable<String, TaxonComparisonResult> scoreMatrix = new Hashtable<String, TaxonComparisonResult>(); //string: ref-comp indices => TaxonComparisonResult 
	ArrayList<TaxonConcept> refConcepts = new ArrayList<TaxonConcept> ();
	ArrayList<TaxonConcept> compConcepts = new ArrayList<TaxonConcept> ();

	public ScoreMatrix(ArrayList<TaxonConcept> refConcepts, ArrayList<TaxonConcept> compConcepts) {
		this.refConcepts = refConcepts;
		this.compConcepts = compConcepts;
	}

	
	public void setScore(TaxonConcept ref, TaxonConcept comp, TaxonComparisonResult score){
		int refIndex = refConcepts.indexOf(ref);
		int compIndex = compConcepts.indexOf(comp);
		scoreMatrix.put(refIndex+":"+compIndex, score);
	}
	
	public TaxonComparisonResult getScore(TaxonConcept ref, TaxonConcept comp){
		int refIndex = refConcepts.indexOf(ref);
		int compIndex = compConcepts.indexOf(comp);
		return scoreMatrix.get(refIndex+":"+compIndex);
	}
	
	public int size(){
		return scoreMatrix.size();
	}
	
	public ArrayList<TaxonComparisonResult> getScores(){
		return new ArrayList<TaxonComparisonResult>(scoreMatrix.values());
	}
	
}
