package edu.arizona.biosemantics.taxoncomparison.comparison.taxon.lib;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import edu.arizona.biosemantics.common.taxonomy.Taxon;
import edu.arizona.biosemantics.euler.alignment.shared.model.Articulation;
import edu.arizona.biosemantics.taxoncomparison.comparison.taxon.ArticulationsGenerator;
import edu.arizona.biosemantics.taxoncomparison.comparison.taxon.RelationGenerator;
import edu.arizona.biosemantics.taxoncomparison.model.ArticulationProposal;
import edu.arizona.biosemantics.taxoncomparison.model.CharacterizedTaxon;
import edu.arizona.biosemantics.taxoncomparison.model.RelationProposal;
import edu.arizona.biosemantics.taxoncomparison.model.Taxonomy;

public class AllCombinationsArticulationsGenerator implements ArticulationsGenerator {

	private RelationGenerator articulationGenerator;

	public AllCombinationsArticulationsGenerator(RelationGenerator articulationGenerator) {
		this.articulationGenerator = articulationGenerator;
	}
	
	@Override
	public Collection<ArticulationProposal> generate(Taxonomy taxonomyA, Taxonomy taxonomyB) {
		Set<ArticulationProposal> result = new HashSet<ArticulationProposal>();
		for(CharacterizedTaxon characterizedTaxonA : taxonomyA.getTaxaDFS()) {
			for(CharacterizedTaxon characterizedTaxonB : taxonomyB.getTaxaDFS()) {
				result.add(new ArticulationProposal(characterizedTaxonA, characterizedTaxonB, 
						articulationGenerator.generate(characterizedTaxonA, characterizedTaxonB)));
			}
		}
		return result;
	}

}
