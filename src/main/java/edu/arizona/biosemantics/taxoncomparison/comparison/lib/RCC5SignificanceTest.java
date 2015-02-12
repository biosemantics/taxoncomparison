package edu.arizona.biosemantics.taxoncomparison.comparison.lib;


import java.util.ArrayList;

import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.stat.inference.TestUtils;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import edu.arizona.biosemantics.taxoncomparison.Configuration;
import edu.arizona.biosemantics.taxoncomparison.model.ArticulationProposal;
import edu.arizona.biosemantics.taxoncomparison.model.RCC5Relation;
import edu.arizona.biosemantics.taxoncomparison.model.ScoreMatrix;
import edu.arizona.biosemantics.taxoncomparison.model.TaxonComparisonResult;
import edu.arizona.biosemantics.taxoncomparison.model.TaxonConcept;

/**
 * 
 * @author Hong Cui
 * one sample T test: normal distribution, sample values are independent from each other. 
 * Wilcoxon one-sample signed rank test (direction and rank of the difference): symmetric distribution, sample values are independent from each other. 
 * Wilcoxon one-sample sige test(direction of the difference: + or -):  sample values are independent from each other.

 */
public class RCC5SignificanceTest {

	
	private float disjointSimMax;
	private float symDiffMax;
	private float congruenceSimMin;
	private float inclusionSimMin;
	private float asymDiffMin;

	@Inject
	public RCC5SignificanceTest(@Named("DisjointSimMax") float disjointSimMax,
			@Named("SymDiffMax") float symDiffMax,
			@Named("CongruenceSimMin") float congruenceSimMin,
			@Named("InclusionSimMin") float inclusionSimMin,
			@Named("AsymDiffMin") float asymDiffMin) {
		this.disjointSimMax = disjointSimMax;
		this.symDiffMax = symDiffMax;
		this.congruenceSimMin = congruenceSimMin;
		this.inclusionSimMin = inclusionSimMin;
		this.asymDiffMin = asymDiffMin;
	}

	

	/**
	 * see https://docs.google.com/document/d/1FQatn1bJDr-v7lllM0YBJV2m65wx63LpU_X4rPqHD4Q/edit?usp=sharing
	 * 
	 * 
	 * 
	 * @param refConcept
	 * @param compConcept
	 * @param scores
	 */
	
	public void calculate(TaxonConcept refConcept, TaxonConcept compConcept, ScoreMatrix scores)  {
		ArrayList<ArticulationProposal> aps = new ArrayList<ArticulationProposal>();
		TaxonComparisonResult tcr = scores.getScore(refConcept, compConcept);
		tcr.setDisjointSimMax(disjointSimMax);
		tcr.setSymDiffMax(symDiffMax);
		tcr.setCongruenceSimMin(congruenceSimMin);
		tcr.setInclusionSimMin(inclusionSimMin);
		tcr.setAsymDiffMax(asymDiffMin);
		double sim = tcr.getSimiliarityScore().getSimilarity();
		double oppSim = tcr.getSimiliarityScore().getOppSimilarity();
		double diffSim = Math.abs(sim - oppSim);
		double[] sims = new double[scores.size()];
		double[] oppSims = new double[scores.size()];
		double[] diffSims = new double[scores.size()];
		int i = 0;
		for(TaxonComparisonResult r : scores.getScores()){
			sims[i] = r.getSimiliarityScore().getSimilarity();
			oppSims[i] = r.getSimiliarityScore().getOppSimilarity();
			diffSims[i] = Math.abs(r.getSimiliarityScore().getSimilarity() -  r.getSimiliarityScore().getOppSimilarity());
			i++;
		}
		
		double simP=Double.NaN, oppSimP=Double.NaN, diffSimP=Double.NaN;
		try{
			simP = TestUtils.tTest(sim, sims); 
			oppSimP = TestUtils.tTest(oppSim, oppSims);
			diffSimP = TestUtils.tTest(diffSim, diffSims);
		}catch(NumberIsTooSmallException e){
			e.printStackTrace();
		}catch(MaxCountExceededException e){
			e.printStackTrace();
		}catch(NullArgumentException e){
			e.printStackTrace();
		}
		
		//overlap
		if(sim >disjointSimMax  && sim < congruenceSimMin /*&& oppSim >disjointSimMax*/  && diffSim <symDiffMax){
			ArticulationProposal ap = new ArticulationProposal();
			ap.setRcc5Relation(RCC5Relation.overlap); 
			//ap.setConfidence(1-(simP+oppSimP)/2);
			ap.setConfidence(1-(simP+diffSimP)/2);
			aps.add(ap);
		}
		
		//disjoint
		if(sim < disjointSimMax /*&& oppSim < disjointSimMax*/ && diffSim < symDiffMax){
			ArticulationProposal ap = new ArticulationProposal();
			ap.setRcc5Relation(RCC5Relation.disjoint);
			//ap.setConfidence(1-(simP+oppSimP)/2);
			ap.setConfidence(1-(simP+diffSimP)/2);
			aps.add(ap);
		}
		
		//congruent
		if(sim > congruenceSimMin /*&& oppSim > congruenceSimMin*/ && diffSim < symDiffMax){
			ArticulationProposal ap = new ArticulationProposal();
			ap.setRcc5Relation(RCC5Relation.congruent);
			//ap.setConfidence(1-(simP+oppSimP)/2);
			ap.setConfidence(1-(simP+diffSimP)/2);
			aps.add(ap);
		}
		
		//inclusion: comp included in ref (all characters of ref are characters of comp): lower taxon have bigger character space.
		if((sim > inclusionSimMin /*&& oppSim > 0d*/ && diffSim > asymDiffMin) ||
				sim >congruenceSimMin && diffSim > symDiffMax){
			ArticulationProposal ap = new ArticulationProposal();
			ap.setRcc5Relation(RCC5Relation.inclusion);
			//ap.setConfidence(1-diffSimP);
			ap.setConfidence(1-(simP+diffSimP)/2);
			aps.add(ap);
		}
		
		tcr.setArticulationProposals(aps);
	}




	
}
