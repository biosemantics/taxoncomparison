/**
 * 
 */
package edu.arizona.biosemantics.taxoncomparison.comparison.lib;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import edu.arizona.biosemantics.matrixreview.shared.model.core.Character;
import edu.arizona.biosemantics.matrixreview.shared.model.core.Organ;
import edu.arizona.biosemantics.matrixreview.shared.model.core.Taxon;
import edu.arizona.biosemantics.matrixreview.shared.model.core.Value;
import edu.arizona.biosemantics.taxoncomparison.comparison.ICharacterComparison;
import edu.arizona.biosemantics.taxoncomparison.model.AsymmetricSimilarityScore;
import edu.arizona.biosemantics.taxoncomparison.model.OrganCharacterState;
import edu.arizona.biosemantics.taxoncomparison.model.CharacterSetComparisonResult;
import edu.arizona.biosemantics.taxoncomparison.model.TaxonComparisonResult;

/**
 * @author Hong Cui
 *
 */
public class ContainmentIndex extends CharacterComparisonMethod implements ICharacterComparison {

	/**
	 * 
	 */
	public ContainmentIndex() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param reference e.g. leaf broad
	 * @param comparsion e.g. leaf blade broad
	 * @return
	 */
	public AsymmetricSimilarityScore compare(OrganCharacterState reference, OrganCharacterState comparison){
		AsymmetricSimilarityScore ccr = new AsymmetricSimilarityScore();
		//simplest: string overlapping
		Vector<String> refTokens = new Vector<String>();
		refTokens.addAll(Arrays.asList(reference.getCharacter().getCharacter().getName().split("\\s+"))); 
		refTokens.addAll(Arrays.asList(reference.getOrgan().getName().split("\\s+")));
		refTokens.addAll(Arrays.asList(reference.getState().getValue().split("\\s+")));
		Vector<String> comTokens = new Vector<String>();
		comTokens.addAll(Arrays.asList(comparison.getCharacter().getCharacter().getName().split("\\s+")));
		comTokens.addAll(Arrays.asList(comparison.getOrgan().getName().split("\\s+")));
		comTokens.addAll(Arrays.asList(comparison.getState().getValue().split("\\s+")));
		
		//ref:[architecture, flower, regular], comp: [fusion, distinct, stamen, united, |, quite, distinct]
		//sim: 0; oppSim:0
		float overlap = 0f;
		for(String ref: refTokens){
			if(comTokens.contains(ref)) overlap++;
		}
		ccr.setSimilarity(overlap/refTokens.size());
		ccr.setOppSimilarity(overlap/comTokens.size());
				
		return ccr;
	}
	


}
