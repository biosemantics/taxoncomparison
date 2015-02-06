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
	float inclusionToleranceMin;
	float overlapToleranceMax;
	float overlapToleranceMin;
	float disjointToleranceMax;
	float congruenceToleranceMin;
	
	@Inject
	public RCC5SignificanceTest(@Named("InclusionToleranceMin") float inclusionToleranceMin,
			@Named("OverlapToleranceMax") float overlapToleranceMax,
			@Named("OverlapToleranceMin") float overlapToleranceMin,
			@Named("DisjointToleranceMax") float disjointToleranceMax,
			@Named("CongruenceToleranceMin") float congruenceToleranceMin) {
		this.inclusionToleranceMin = inclusionToleranceMin;
		this.overlapToleranceMax = overlapToleranceMax;
		this.overlapToleranceMin = overlapToleranceMin;
		this.disjointToleranceMax = overlapToleranceMax;
		this.congruenceToleranceMin = congruenceToleranceMin;
	}

	/*public RCC5SignificanceTest(Configuration config) {
		this.inclusionToleranceMin = config.getInclusionToleranceMin();
		this.overlapToleranceMax = config.overlapToleranceMax;
		this.overlapToleranceMin = config.congruenceToleranceMin;
		this.disjointToleranceMax = config.disjointToleranceMax;
		this.congruenceToleranceMin = config.congruenceToleranceMin;
	}*/

	/**
	 * if sim > oppSim
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
		tcr.setOverlapToleranceMin(overlapToleranceMin);
		tcr.setOverlapToleranceMax(overlapToleranceMax);
		tcr.setInclusionToleranceMin(inclusionToleranceMin);
		tcr.setDisjointToleranceMax(disjointToleranceMax);
		tcr.setCongruenceToleranceMin(congruenceToleranceMin);
		double sim = tcr.getSimiliarityScore().getSimilarity();
		double oppSim = tcr.getSimiliarityScore().getOppSimilarity();
		double diffSim = sim - oppSim;
		double[] sims = new double[scores.size()];
		double[] oppSims = new double[scores.size()];
		double[] diffSims = new double[scores.size()];
		int i = 0;
		for(TaxonComparisonResult r : scores.getScores()){
			sims[i] = r.getSimiliarityScore().getSimilarity();
			oppSims[i] = r.getSimiliarityScore().getOppSimilarity();
			diffSims[i] = r.getSimiliarityScore().getSimilarity() -  r.getSimiliarityScore().getOppSimilarity();
			i++;
		}
		
		double simP=Double.NaN, oppSimP=Double.NaN, diffSimP=Double.NaN;
		try{
			simP = TestUtils.tTest(sim, sims) / 2;
			oppSimP = TestUtils.tTest(oppSim, oppSims) / 2;
			diffSimP = TestUtils.tTest(diffSim, diffSims) / 2;
		}catch(NumberIsTooSmallException e){
			e.printStackTrace();
		}catch(MaxCountExceededException e){
			e.printStackTrace();
		}catch(NullArgumentException e){
			e.printStackTrace();
		}
		
		//overlap
		if(sim > overlapToleranceMin && oppSim < overlapToleranceMax){
			ArticulationProposal ap = new ArticulationProposal();
			ap.setRcc5Relation("overlap"); 
			ap.setConfidence((simP+oppSimP)/2);
			aps.add(ap);
		}
		
		//disjoint
		if(sim < disjointToleranceMax && oppSim < disjointToleranceMax){
			ArticulationProposal ap = new ArticulationProposal();
			ap.setRcc5Relation("disjoint");
			ap.setConfidence((simP+oppSimP)/2);
			aps.add(ap);
		}
		
		//congruent
		if(sim > congruenceToleranceMin && oppSim > congruenceToleranceMin){
			ArticulationProposal ap = new ArticulationProposal();
			ap.setRcc5Relation("congruent");
			ap.setConfidence((simP+oppSimP)/2);
			aps.add(ap);
		}
		
		//inclusion: comp included in ref (all characters of ref are characters of comp): lower taxon have bigger character space.
		if(diffSim > inclusionToleranceMin && oppSim > 0d){
			ArticulationProposal ap = new ArticulationProposal();
			ap.setRcc5Relation("inclusion");
			ap.setConfidence(diffSimP);
			aps.add(ap);
		}
		
		tcr.setArticulationProposals(aps);
	}




	
}
