package edu.arizona.biosemantics.taxoncomparison.comparison.taxon.lib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.stat.inference.TestUtils;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import edu.arizona.biosemantics.common.log.LogLevel;
import edu.arizona.biosemantics.common.taxonomy.Taxon;
import edu.arizona.biosemantics.euler.alignment.shared.model.Articulation;
import edu.arizona.biosemantics.euler.alignment.shared.model.Relation;
import edu.arizona.biosemantics.taxoncomparison.comparison.characterstate.CharacterStateSimilarityMetric;
import edu.arizona.biosemantics.taxoncomparison.comparison.taxon.RelationGenerator;
import edu.arizona.biosemantics.taxoncomparison.model.AsymmetricSimilarity;
import edu.arizona.biosemantics.taxoncomparison.model.CharacterState;
import edu.arizona.biosemantics.taxoncomparison.model.CharacterizedTaxon;
import edu.arizona.biosemantics.taxoncomparison.model.RelationProposal;
import edu.arizona.biosemantics.taxoncomparison.model.Taxonomy;

public class TTestBasedRelationGenerator implements RelationGenerator {
	
	private double disjointSimilarityMax;
	private double symmmetricDifferenceMax;
	private double congruenceSimilarityMin;
	private double inclusionSimilarityMin;
	private double asymmetricDifferenceMin;
	
	private CharacterStateSimilarityMetric characterStateSimilarityMetric;
	private Map<CharacterizedTaxon, Map<CharacterizedTaxon, AsymmetricSimilarity<CharacterizedTaxon>>> betweenTaxonomyScores;
	private Taxonomy taxonomyA;
	private Taxonomy taxonomyB;
	private double[] similarities;
	private double[] oppositeSimilarities;
	private double[] differencesSimilarities;
	
	public TTestBasedRelationGenerator(@Named("DisjointSimMax") double disjointSimilarityMax,
			@Named("SymmmetricDiffMax") double symmmetricDifferenceMax,
			@Named("CongruenceSimilarityMin") double congruenceSimilarityMin,
			@Named("InclusionSimilarityMin") double inclusionSimilarityMin,
			@Named("AsymmetricDifferenceMin") double asymmetricDifferenceMin,
			CharacterStateSimilarityMetric characterStateSimilarityMetric, 
			Taxonomy taxonomyA, Taxonomy taxonomyB) {
		this.disjointSimilarityMax = disjointSimilarityMax;
		this.symmmetricDifferenceMax = symmmetricDifferenceMax;
		this.congruenceSimilarityMin = congruenceSimilarityMin;
		this.inclusionSimilarityMin = inclusionSimilarityMin;
		this.asymmetricDifferenceMin = asymmetricDifferenceMin;
		
		this.characterStateSimilarityMetric = characterStateSimilarityMetric;
		this.taxonomyA = taxonomyA;
		this.taxonomyB = taxonomyB;
		initializeBetweenTaxonomyScores(taxonomyA, taxonomyB);
	}

	/*wrong idea, making all taxon in one taxonomy the same
	//union the characters of the concept and all its lower concepts
	HashSet<OrganCharacterState> refCharacters = new HashSet<OrganCharacterState>();
	unionCharacters(refCharacters, reference);
	
	HashSet<OrganCharacterState> compCharacters = new HashSet<OrganCharacterState>();
	unionCharacters(compCharacters, comparison);
	*/
	private void initializeBetweenTaxonomyScores(Taxonomy taxonomyA, Taxonomy taxonomyB) {
		log(LogLevel.DEBUG, "initialize between taxonomy scores");
		betweenTaxonomyScores = new HashMap<CharacterizedTaxon, Map<CharacterizedTaxon, AsymmetricSimilarity<CharacterizedTaxon>>>();

		for(CharacterizedTaxon taxonA : taxonomyA.getTaxaDFS()) {
			betweenTaxonomyScores.put(taxonA, new HashMap<CharacterizedTaxon, AsymmetricSimilarity<CharacterizedTaxon>>());
			for(CharacterizedTaxon taxonB : taxonomyB.getTaxaDFS()) {
				log(LogLevel.DEBUG, "comparing "+ taxonA.getName() + " to "+ taxonB.getName());
				
				Map<CharacterState, Map<CharacterState, AsymmetricSimilarity<CharacterState>>> betweenTaxaMap = 
						new HashMap<CharacterState, Map<CharacterState, AsymmetricSimilarity<CharacterState>>>();
				Collection<CharacterState> characterStatesA = taxonA.getCharacterStates();
				Collection<CharacterState> characterStatesB = taxonB.getCharacterStates();
				
				//compare two sets of characters
				//create a m*n matrix to save the character-wise comparison
				for(CharacterState characterStateA : characterStatesA) {
					betweenTaxaMap.put(characterStateA, new HashMap<CharacterState, AsymmetricSimilarity<CharacterState>>());
					for(CharacterState characterStateB : characterStatesB) {
						AsymmetricSimilarity<CharacterState> asymmetricSimilarity = characterStateSimilarityMetric.get(characterStateA, characterStateB);
						betweenTaxaMap.get(characterStateA).put(characterStateB, asymmetricSimilarity);
						log(LogLevel.DEBUG, characterStateA + " vs " + characterStateB + " = " + 
								asymmetricSimilarity.getSimilarity() + " " + asymmetricSimilarity.getOppositeSimilarity());
					}
				}
				
				//get the aggregated score
				//1. simplest: 
				double sumSimilarity = 0;
				double sumOppositeSimilarity = 0;
				//in each row, find the max sim
				//sum up all max sim from all rows
				//div the sum by row number = sim
				for(CharacterState characterStateA : characterStatesA) {
					double maxSimilarity = 0;
					for(CharacterState characterStateB : characterStatesB) {
						double similarity = betweenTaxaMap.get(characterStateA).get(characterStateB).getSimilarity();
						if(similarity > maxSimilarity) 
							maxSimilarity = similarity;
					}
					sumSimilarity += maxSimilarity;
				}
				double similarity = sumSimilarity / characterStatesA.size();
				
				//in each column, find the max sim
				//sum up all max sim from all columns
				//div the sum by column number = oppSim
				for(CharacterState characterStateB : characterStatesB) {
					double maxOppositeSimilarity = 0;
					for(CharacterState characterStateA : characterStatesA) {
						double oppositeSimilarity = betweenTaxaMap.get(characterStateA).get(characterStateB).getOppositeSimilarity();
						if(oppositeSimilarity > maxOppositeSimilarity) 
							maxOppositeSimilarity = oppositeSimilarity;
					}
					sumOppositeSimilarity += maxOppositeSimilarity;
				}
				double oppositeSimilarity = sumOppositeSimilarity / characterStatesB.size();
				
				betweenTaxonomyScores.get(taxonA).put(taxonB, new AsymmetricSimilarity<CharacterizedTaxon>(taxonA, taxonB, 
						similarity, oppositeSimilarity));
			}
		}
		
		int comparisonSize = taxonomyA.getTaxaDFS().size() * taxonomyB.getTaxaDFS().size();
		similarities = new double[comparisonSize];
		oppositeSimilarities = new double[comparisonSize];
		differencesSimilarities = new double[comparisonSize];
		int i = 0;
		for(CharacterizedTaxon characterizedTaxonA : taxonomyA.getTaxaDFS()) {
			for(CharacterizedTaxon characterizedTaxonB : taxonomyB.getTaxaDFS()) {
				similarities[i] = betweenTaxonomyScores.get(characterizedTaxonA).get(characterizedTaxonB).getSimilarity();
				oppositeSimilarities[i] = betweenTaxonomyScores.get(characterizedTaxonA).get(characterizedTaxonB).getOppositeSimilarity();
				differencesSimilarities[i] = Math.abs(similarities[i] -  oppositeSimilarities[i]);
				i++;
			}
		}
	}

	@Override
	public Set<RelationProposal> generate(CharacterizedTaxon taxonA, CharacterizedTaxon taxonB) {		
		log(LogLevel.DEBUG, "Generate relation proposals for " + taxonA.getName() + " " + taxonB.getName());
		Set<RelationProposal> result = new HashSet<RelationProposal>();
		
		AsymmetricSimilarity<CharacterizedTaxon> asymmetricSimilarity = betweenTaxonomyScores.get(taxonA).get(taxonB);
		double similarity = asymmetricSimilarity.getSimilarity();
		double oppositeSimilarity = asymmetricSimilarity.getOppositeSimilarity();
		double similarityDifference = Math.abs(similarity - oppositeSimilarity);		
		double simP=Double.NaN, oppSimP=Double.NaN, diffSimP=Double.NaN;
		try{
			simP = TestUtils.tTest(similarity, similarities); 
			oppSimP = TestUtils.tTest(oppositeSimilarity, oppositeSimilarities);
			diffSimP = TestUtils.tTest(similarityDifference, differencesSimilarities);
		}catch(NumberIsTooSmallException e){
			e.printStackTrace();
		}catch(MaxCountExceededException e){
			e.printStackTrace();
		}catch(NullArgumentException e){
			e.printStackTrace();
		}
		
		if(isOverlap(similarity, oppositeSimilarity, similarityDifference)) 
			result.add(createProposal(Relation.OVERLAP, (1 - (simP + diffSimP) / 2), similarity, oppositeSimilarity));
		if(isDisjoint(similarity, oppositeSimilarity, similarityDifference)) 
			result.add(createProposal(Relation.DISJOINT, (1 - (simP + diffSimP) / 2), similarity, oppositeSimilarity));
		if(isCongruent(similarity, oppositeSimilarity, similarityDifference)) 
			result.add(createProposal(Relation.CONGRUENT, (1 - (simP + diffSimP) / 2), similarity, oppositeSimilarity));
		if(isInclusion(similarity, oppositeSimilarity, similarityDifference)) 
			result.add(createProposal(Relation.A_INCLUDES_B, (1 - (simP + diffSimP) / 2), similarity, oppositeSimilarity));

		log(LogLevel.DEBUG, "Similarity = " + similarity);
		log(LogLevel.DEBUG, "OppSimilarity = " + oppositeSimilarity);
		log(LogLevel.DEBUG, "Proposed relations: " + result);
		return result;
	}

	//inclusion: comp included in ref (all characters of ref are characters of comp): lower taxon have bigger character space.
	private boolean isInclusion(double similarity, double oppositeSimilarity, double similarityDifference) {
		return (similarity > inclusionSimilarityMin /*&& oppSim > 0d*/ && similarityDifference > asymmetricDifferenceMin) ||
				(similarity > congruenceSimilarityMin && similarityDifference > symmmetricDifferenceMax);
	}

	private boolean isCongruent(double similarity, double oppositeSimilarity, double similarityDifference) {
		return similarity > congruenceSimilarityMin /*&& oppSim > congruenceSimMin*/ && similarityDifference < symmmetricDifferenceMax;
	}

	private boolean isDisjoint(double similarity, double oppositeSimilarity, double similarityDifference) {
		return similarity < disjointSimilarityMax /*&& oppSim < disjointSimMax*/ && similarityDifference < symmmetricDifferenceMax;
	}
	
	private boolean isOverlap(double similarity, double oppositeSimilarity,	double similarityDifference) {
		return similarity > disjointSimilarityMax  && similarity < congruenceSimilarityMin /*&& oppSim >disjointSimMax*/  && similarityDifference < symmmetricDifferenceMax;
	}

	private RelationProposal createProposal(Relation relation, double confidence, double similarity, double oppositeSimilarity) {
		RelationProposal relationProposal = new RelationProposal();
		relationProposal.similarity = similarity;
		relationProposal.oppositeSimilarity = oppositeSimilarity;
		relationProposal.relation = relation; 
		//ap.setConfidence(1-(simP+oppSimP)/2);
		relationProposal.confidence = confidence;
		return relationProposal;
	}
}
