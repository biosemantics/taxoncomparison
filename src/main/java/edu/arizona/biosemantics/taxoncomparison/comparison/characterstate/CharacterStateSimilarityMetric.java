package edu.arizona.biosemantics.taxoncomparison.comparison.characterstate;

import edu.arizona.biosemantics.taxoncomparison.model.AsymmetricSimilarity;
import edu.arizona.biosemantics.taxoncomparison.model.CharacterState;

public interface CharacterStateSimilarityMetric {

	public AsymmetricSimilarity<CharacterState> get(CharacterState characterStateA, CharacterState characterStateB);
	
}
