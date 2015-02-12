package edu.arizona.biosemantics.taxoncomparison.comparison.lib;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;

import edu.arizona.biosemantics.common.log.LogLevel;
import edu.arizona.biosemantics.matrixreview.shared.model.Model;
import edu.arizona.biosemantics.taxoncomparison.Configuration;
import edu.arizona.biosemantics.taxoncomparison.io.MatrixReader;
import edu.arizona.biosemantics.taxoncomparison.model.ScoreMatrix;
import edu.arizona.biosemantics.taxoncomparison.model.TaxonComparisonResult;
import edu.arizona.biosemantics.taxoncomparison.model.TaxonConcept;
import edu.arizona.biosemantics.taxoncomparison.model.TaxonConceptRepo;

public class SimilarityBasedRCC5 {
	private  String filePath2ReferenceMatrix;
	private  String filePath2ComparisonMatrix;
	private  String filePath2CharacterWeights;
	private  RCC5SignificanceTest RCC5st;

	
	@Inject
	public SimilarityBasedRCC5(@Named("FilePath2ReferenceMatrix") String filePath2ReferenceMatrix,
			@Named("FilePath2CharacterWeights") String filePath2CharacterWeights,
			@Named("FilePath2ComparisonMatrix") String filePath2ComparisonMatrix, 
			RCC5SignificanceTest RCC5st) {
		this.filePath2CharacterWeights = filePath2CharacterWeights;
		this.filePath2ComparisonMatrix = filePath2ComparisonMatrix;
		this.filePath2ReferenceMatrix = filePath2ReferenceMatrix;
		this.RCC5st = RCC5st;

	}
	
	public ArrayList<TaxonComparisonResult> compute(){
		ArrayList<TaxonComparisonResult> results = new ArrayList<TaxonComparisonResult>();
		try {
			//allow the user to choose taxon concepts from 2 [multiple] matrices
			MatrixReader reader = new MatrixReader();
			
			//Hashtable<String, Float> weights = unserializeWeights(m.config.getFilePath2CharacterWeights());
			Hashtable<String, Float> weights = null;
			//weights covers the characters from both matrices
			Model model = unserializeMatrix(filePath2ReferenceMatrix); 
			TaxonConceptRepo refRepo = reader.read(model);
			if(weights !=null) reader.applyWeights(refRepo, weights);
			model = unserializeMatrix(filePath2ComparisonMatrix); 
			TaxonConceptRepo compRepo = reader.read(model);
			if(weights !=null) reader.applyWeights(compRepo, weights);


			ArrayList<TaxonConcept> refConcepts =new ArrayList<TaxonConcept> (refRepo.getTaxonConcepts().values());
			ArrayList<TaxonConcept> compConcepts =new ArrayList<TaxonConcept> (compRepo.getTaxonConcepts().values());
			ScoreMatrix scores = new ScoreMatrix(refConcepts, compConcepts);
			TaxonConceptComparisonMethod method = new TaxonConceptComparisonMethod();
			for(TaxonConcept refConcept: refConcepts){ //
				for(TaxonConcept compConcept: compConcepts){
					TaxonComparisonResult tcr = method.compare(refConcept, compConcept); //similarity score set in tcr
					//AsymmetricSimilarityScore asc = tcr.getSimiliarityScore();
					//popuplate these values in a m x n matrix
					scores.setScore(refConcept, compConcept, tcr);
				}
			}
			//populate scores with RCC5 proposals
			//output scores
			
			for(TaxonConcept refConcept: refConcepts){ //
				for(TaxonConcept compConcept: compConcepts){
					RCC5st.calculate(refConcept, compConcept, scores);
					TaxonComparisonResult tcr =	scores.getScore(refConcept, compConcept);
					log(LogLevel.DEBUG, "comp concept: "+compConcept.getTaxon().getFullName());
					log(LogLevel.DEBUG, "ref concept: "+refConcept.getTaxon().getFullName());
					log(LogLevel.DEBUG, tcr.toString());
					if(!tcr.getArticulationProposals().isEmpty()){
						System.out.println("comp concept: "+compConcept.getTaxon().getFullName());
						System.out.println("ref concept: "+refConcept.getTaxon().getFullName());				
						System.out.println(tcr.toString());
						System.out.println();
						results.add(tcr);
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Throwable t){
			t.printStackTrace();
		}
		return results;
	}
	
	private static Model unserializeMatrix(String file) throws FileNotFoundException, IOException, ClassNotFoundException {
		try(ObjectInput input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
			Model model = (Model)input.readObject();
			return model;
		}
	}
	
	private static Hashtable<String, Float> unserializeWeights(String file) throws FileNotFoundException, IOException, ClassNotFoundException {
		try(ObjectInput input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
			Hashtable<String, Float> weights = (Hashtable<String, Float>)input.readObject();
			return weights;
		}
	}
}