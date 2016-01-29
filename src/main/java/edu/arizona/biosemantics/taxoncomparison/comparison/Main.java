package edu.arizona.biosemantics.taxoncomparison.comparison;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.Collection;

import edu.arizona.biosemantics.matrixreview.shared.model.Model;
import edu.arizona.biosemantics.taxoncomparison.comparison.characterstate.CharacterStateSimilarityMetric;
import edu.arizona.biosemantics.taxoncomparison.comparison.characterstate.lib.ContainmentCharacterStateSimilarityMetric;
import edu.arizona.biosemantics.taxoncomparison.comparison.taxon.RelationGenerator;
import edu.arizona.biosemantics.taxoncomparison.comparison.taxon.lib.AllCombinationsArticulationsGenerator;
import edu.arizona.biosemantics.taxoncomparison.comparison.taxon.lib.TTestBasedRelationGenerator;
import edu.arizona.biosemantics.taxoncomparison.io.MatrixReviewModelReader;
import edu.arizona.biosemantics.taxoncomparison.model.ArticulationProposal;
import edu.arizona.biosemantics.taxoncomparison.model.RelationProposal;
import edu.arizona.biosemantics.taxoncomparison.model.Taxonomy;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {
		createAllArticulations();
	}
	
	private static void createAllArticulations() throws FileNotFoundException, ClassNotFoundException, IOException {
		Model modelA = unserializeMatrix("TaxonMatrix.ser");
		Model modelB = unserializeMatrix("TaxonMatrix.ser");
		MatrixReviewModelReader matrixReviewModelReader = new MatrixReviewModelReader();
		Taxonomy taxonomyA = matrixReviewModelReader.getTaxonomy(modelA);
		Taxonomy taxonomyB = matrixReviewModelReader.getTaxonomy(modelB);
		
		CharacterStateSimilarityMetric characterStateSimilarityMetric = new ContainmentCharacterStateSimilarityMetric();
		RelationGenerator pairwiseArticulationGenerator = new TTestBasedRelationGenerator(Configuration.disjointSimilarityMax, 
				Configuration.symmetricDifferenceMax, Configuration.congurenceSimilarityMin, Configuration.inclusionSimilarityMin, 
				Configuration.asymmetricDifferenceMin, characterStateSimilarityMetric, taxonomyA, taxonomyB);
		AllCombinationsArticulationsGenerator allCombinationsArticulationsGenerator = new AllCombinationsArticulationsGenerator(pairwiseArticulationGenerator);	
		Collection<ArticulationProposal> result = allCombinationsArticulationsGenerator.generate(taxonomyA, taxonomyB);
		System.out.println(result);
	}

	private static Model unserializeMatrix(String file) throws FileNotFoundException, IOException, ClassNotFoundException {
		try(ObjectInput input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
			Model model = (Model)input.readObject();
			return model;
		}
	}
}
