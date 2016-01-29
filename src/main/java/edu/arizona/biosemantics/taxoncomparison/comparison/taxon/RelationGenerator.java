package edu.arizona.biosemantics.taxoncomparison.comparison.taxon;

import java.util.Set;

import edu.arizona.biosemantics.euler.alignment.shared.model.Articulation;
import edu.arizona.biosemantics.taxoncomparison.model.CharacterizedTaxon;
import edu.arizona.biosemantics.taxoncomparison.model.RelationProposal;
import edu.arizona.biosemantics.taxoncomparison.model.Taxonomy;

public interface RelationGenerator {

	public Set<RelationProposal> generate(CharacterizedTaxon taxonA, CharacterizedTaxon taxonB);
	
}
