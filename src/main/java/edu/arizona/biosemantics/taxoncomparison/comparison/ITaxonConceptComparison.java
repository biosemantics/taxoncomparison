package edu.arizona.biosemantics.taxoncomparison.comparison;

import edu.arizona.biosemantics.taxoncomparison.model.TaxonComparisonResult;
import edu.arizona.biosemantics.taxoncomparison.model.TaxonConcept;

public interface ITaxonConceptComparison {
	public TaxonComparisonResult compare(TaxonConcept reference, TaxonConcept comparison);
}
