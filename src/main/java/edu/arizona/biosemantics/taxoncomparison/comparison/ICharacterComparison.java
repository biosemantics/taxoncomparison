/**
 * 
 */
package edu.arizona.biosemantics.taxoncomparison.comparison;

import java.util.List;

import edu.arizona.biosemantics.taxoncomparison.model.AsymmetricSimilarityScore;
import edu.arizona.biosemantics.taxoncomparison.model.OrganCharacterState;
import edu.arizona.biosemantics.taxoncomparison.model.CharacterSetComparisonResult;
import edu.arizona.biosemantics.taxoncomparison.model.TaxonComparisonResult;
/**
 * @author Hong Cui
 *
 */
public interface ICharacterComparison {
	//may not be symmetric
	public AsymmetricSimilarityScore compare(OrganCharacterState reference, OrganCharacterState comparison);
	//TaxonomyComparisonResult compareTaxonomy(List<Taxon> reference, List<Taxon> comparision); // taxon and its lower taxa
}
