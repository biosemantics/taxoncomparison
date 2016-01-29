package edu.arizona.biosemantics.taxoncomparison.comparison.taxon;

import java.util.Collection;

import edu.arizona.biosemantics.taxoncomparison.model.ArticulationProposal;
import edu.arizona.biosemantics.taxoncomparison.model.RelationProposal;
import edu.arizona.biosemantics.taxoncomparison.model.Taxonomy;

public interface ArticulationsGenerator {
	
	public Collection<ArticulationProposal> generate(Taxonomy taxonomyA, Taxonomy taxonomyB);
	
}
