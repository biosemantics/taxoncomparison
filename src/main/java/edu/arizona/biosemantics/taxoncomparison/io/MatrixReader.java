/**
 * 
 */
package edu.arizona.biosemantics.taxoncomparison.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.arizona.biosemantics.matrixreview.shared.model.Model;
import edu.arizona.biosemantics.matrixreview.shared.model.core.Taxon;
import edu.arizona.biosemantics.matrixreview.shared.model.core.Character;
import edu.arizona.biosemantics.matrixreview.shared.model.core.Organ;
import edu.arizona.biosemantics.matrixreview.shared.model.core.TaxonMatrix;
import edu.arizona.biosemantics.matrixreview.shared.model.core.Value;
import edu.arizona.biosemantics.taxoncomparison.model.TaxonConcept;
import edu.arizona.biosemantics.taxoncomparison.model.OrganCharacterState;
import edu.arizona.biosemantics.taxoncomparison.model.TaxonConceptRepo;
import edu.arizona.biosemantics.taxoncomparison.model.WeightedCharacter;



/**
 * @author Hong Cui
 *
 */
public class MatrixReader {



	/**
	 * read a matrix to populate data model for taxon comparison use
	 */
	public MatrixReader() {
	}

	/**
	 * 
	 * @param matrixModel
	 * @param weights can not be null
	 * @return
	 * @throws Throwable
	 */
	public TaxonConceptRepo read(Model matrixModel) throws Throwable{

		TaxonConceptRepo repo = new TaxonConceptRepo();
		TaxonMatrix matrix = matrixModel.getTaxonMatrix();
		Set<Taxon> taxa = matrix.getTaxa();
		List<Organ> charactersByOrgan = matrix.getHierarchyCharacters();
		for(Taxon taxon: taxa){
			TaxonConcept concept = new TaxonConcept();
			concept.setTaxon(taxon);
			readTaxonConcept(concept, matrix, charactersByOrgan);
			repo.putTaxonConcept(concept);
		}
		//System.out.println(repo.toString());
		return repo;
	}
	
	/**
	 * 
	 * @param repo
	 * @param weights e.g. leaf size => 0.1; how should weights be normalized
	 * @return
	 */
	public void applyWeights(TaxonConceptRepo repo, Hashtable<String, Float> weights){
		Collection<TaxonConcept> concepts = repo.getTaxonConcepts().values();
		Iterator<TaxonConcept> it = concepts.iterator();
		while(it.hasNext()){
			TaxonConcept t = it.next();
			ArrayList<OrganCharacterState> ocss = t.getCharacters();
			for(OrganCharacterState ocs: ocss){
				WeightedCharacter wc = ocs.getCharacter();
				wc.setWeight(weights.get(wc.getCharacter().toString()));
			}
		}
	}
	
	/**
	 * 
	 * @param concept
	 * @param matrix
	 * @param charactersByOrgan
	 */

	private void readTaxonConcept(TaxonConcept concept, TaxonMatrix matrix,
			List<Organ> charactersByOrgan) {
		
		for(Organ organ: charactersByOrgan){
			Set<Character> characters = organ.getCharacters();//[shape of calyx]
			for(Character character: characters){
				WeightedCharacter wCharacter = new WeightedCharacter(null, character); //no weight
				Value state = matrix.getValue(concept.getTaxon(), character);
				if(state!=null && !state.getValue().isEmpty()){ //5(3-7)-parted
					OrganCharacterState ocs = new OrganCharacterState(organ, wCharacter, state);
					concept.addCharacter(ocs);
				}else{
					System.out.println("empty state");
				}
			}
		}

		List<Taxon> children = concept.getTaxon().getChildren();
		if(children==null || children.isEmpty()) return;
		else{
			for(Taxon child: children){
				TaxonConcept childConcept = new TaxonConcept();
				childConcept.setTaxon(child);
				readTaxonConcept(childConcept, matrix, charactersByOrgan);
				concept.addChildTaxon(childConcept);
			}		
		}
	}
	/**
	 * 
	 * @param root
	 * @return 
	 */



	/*private void readTaxonConcept(TaxonConcept root, TaxonMatrix matrix, List<Organ> charactersByOrgan){
		List<Taxon> children = root.getTaxon().getChildren();
		if(children==null || children.isEmpty()) return;
		else{
			for(Taxon child: children){
				TaxonConcept concept = new TaxonConcept();
				concept.setTaxon(child);
				//assemble characters for this child
				for(Organ organ: charactersByOrgan){
					Set<Character> characters = organ.getCharacters();
					for(Character character: characters){
						Value state = matrix.getValue(child, character);
						if(state!=null && !state.getValue().isEmpty()){
							OrganCharacterState ocs = new OrganCharacterState(organ, character, state);
							concept.addCharacter(ocs);
						}
					}
				}
				//add the concept to the root 
				root.addChildTaxon(concept);
				readAllLowerTaxonConcepts(concept, matrix, charactersByOrgan);
			}
		}
	}*/


}
