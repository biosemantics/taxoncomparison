package edu.arizona.biosemantics.taxoncomparison.comparison.characterstate.lib;

import java.util.Arrays;
import java.util.Vector;

import edu.arizona.biosemantics.taxoncomparison.comparison.characterstate.CharacterStateSimilarityMetric;
import edu.arizona.biosemantics.taxoncomparison.model.AsymmetricSimilarity;
import edu.arizona.biosemantics.taxoncomparison.model.CharacterState;

/**
 * Simple string overlapping
 */
public class ContainmentCharacterStateSimilarityMetric implements CharacterStateSimilarityMetric {

	@Override
	public AsymmetricSimilarity<CharacterState> get(CharacterState characterStateA, CharacterState characterStateB) {
		Vector<String> refTokens = new Vector<String>();
		refTokens.addAll(Arrays.asList(characterStateA.getCharacter().split("\\s+"))); 
		refTokens.addAll(Arrays.asList(characterStateA.getOrgan().split("\\s+")));
		refTokens.addAll(Arrays.asList(characterStateA.getState().split("\\s+")));
		Vector<String> comTokens = new Vector<String>();
		comTokens.addAll(Arrays.asList(characterStateB.getCharacter().split("\\s+")));
		comTokens.addAll(Arrays.asList(characterStateB.getOrgan().split("\\s+")));
		comTokens.addAll(Arrays.asList(characterStateB.getState().split("\\s+")));
		
		//ref:[architecture, flower, regular], comp: [fusion, distinct, stamen, united, |, quite, distinct]
		//sim: 0; oppSim:0
		double overlap = 0;
		for(String ref: refTokens){
			if(comTokens.contains(ref)) overlap++;
		}
		
		return new AsymmetricSimilarity<CharacterState>(characterStateA, characterStateB, overlap/refTokens.size(), overlap/comTokens.size());
	}

}
