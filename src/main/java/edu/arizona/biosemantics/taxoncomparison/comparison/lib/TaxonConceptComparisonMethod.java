/**
 * 
 */
package edu.arizona.biosemantics.taxoncomparison.comparison.lib;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;



import edu.arizona.biosemantics.common.log.LogLevel;
import edu.arizona.biosemantics.taxoncomparison.comparison.ITaxonConceptComparison;
import edu.arizona.biosemantics.taxoncomparison.model.AsymmetricSimilarityScore;
import edu.arizona.biosemantics.taxoncomparison.model.OrganCharacterState;
import edu.arizona.biosemantics.taxoncomparison.model.TaxonComparisonResult;
import edu.arizona.biosemantics.taxoncomparison.model.TaxonConcept;

/**
 * @author Hong Cui
 * obtain an aggregate similarity score btw two concepts based on the CharacterComparisonResult of their characters
  */
public class TaxonConceptComparisonMethod implements ITaxonConceptComparison {

	/**
	 * 
	 */
	public TaxonConceptComparisonMethod() {

	}

	@Override
	public TaxonComparisonResult compare(TaxonConcept reference,
			TaxonConcept comparison) {
		/*wrong idea, making all taxon in one taxonomy the same
		//union the characters of the concept and all its lower concepts
		HashSet<OrganCharacterState> refCharacters = new HashSet<OrganCharacterState>();
		unionCharacters(refCharacters, reference);
		
		HashSet<OrganCharacterState> compCharacters = new HashSet<OrganCharacterState>();
		unionCharacters(compCharacters, comparison);
		*/
		
		ArrayList<OrganCharacterState> refCharacters = reference.getCharacters();
		ArrayList<OrganCharacterState> compCharacters = comparison.getCharacters();
		
		ContainmentIndex ji = new ContainmentIndex();
		//compare two sets of characters
		//create a m*n matrix to save the character-wise comparison
		
		log(LogLevel.DEBUG, "");
		System.out.println();
		log(LogLevel.DEBUG, "comparing "+reference.getTaxon().getName() + " to "+comparison.getTaxon().getName());
		System.out.println("comparing "+reference.getTaxon().getName() + " to "+comparison.getTaxon().getName());
		AsymmetricSimilarityScore[][] scores = new AsymmetricSimilarityScore[refCharacters.size()][compCharacters.size()];
		Iterator<OrganCharacterState> refIt = refCharacters.iterator();//rows
		int i = 0; 
		while(refIt.hasNext()){
			OrganCharacterState refChara = refIt.next();
			int j = 0;
			log(LogLevel.DEBUG, i+",");
			System.out.print(i+",");
			Iterator<OrganCharacterState> compIt = compCharacters.iterator();//columns
			while(compIt.hasNext()){
				OrganCharacterState compChara = compIt.next();
				scores[i][j] = ji.compare(refChara, compChara);
				log(LogLevel.DEBUG, "["+scores[i][j].getSimilarity()+"]["+scores[i][j].getOppSimilarity()+"],");
				System.out.print("["+scores[i][j].getSimilarity()+"]["+scores[i][j].getOppSimilarity()+"],");
				j++;
			}
			log(LogLevel.DEBUG,"");
			System.out.println();
			i++;
		}
		
		//get the aggregated score
		//1. simplest: 
		float sumSim = 0f;
		float sumOppSim = 0f;
		//in each row, find the max sim
		//sum up all max sim from all rows
		//div the sum by row number = sim
		for(int j = 0; j < scores.length; j++){
			float max = 0f;
			for(int k = 0; k < scores[j].length; k++ ){
				if(scores[j][k].getSimilarity() > max) max = scores[j][k].getSimilarity();
			}
			sumSim += max;
		}
		float sim = sumSim/scores.length;
		
		//in each column, find the max sim
		//sum up all max sim from all columns
		//div the sum by column number = oppSim
		for(int j = 0; j < scores[0].length; j++){
			float max = 0f;
			for(int k = 0; k < scores.length; k++ ){
				if(scores[k][j].getOppSimilarity() > max) max = scores[k][j].getOppSimilarity();
			}
			sumOppSim += max;
		}
		float oppSim = sumOppSim/scores[0].length;
		
		AsymmetricSimilarityScore asc = new AsymmetricSimilarityScore();
		asc.setSimilarity(sim);
		asc.setOppSimilarity(oppSim);
		TaxonComparisonResult tcr = new TaxonComparisonResult();
		tcr.setReferenceConcept(reference);
		tcr.setComparisonConcept(comparison);
		tcr.setSimiliarityScore(asc);
	
		return tcr;
	}

	/**
	 * recursively add characters from taxon concept hierarchy to characters
	 * @param refCharacters
	 * @param reference
	 */
	private void unionCharacters(HashSet<OrganCharacterState> characters,
			TaxonConcept concept) {
		characters.addAll(concept.getCharacters()); //didn't remove duplicates: comparison based on object id, not character content string, 
		for(TaxonConcept child: concept.getChildrenConcepts()){
			unionCharacters(characters, child); 
		}
		
	}

}
