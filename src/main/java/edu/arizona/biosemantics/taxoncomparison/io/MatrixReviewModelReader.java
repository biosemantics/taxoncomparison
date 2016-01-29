package edu.arizona.biosemantics.taxoncomparison.io;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.arizona.biosemantics.common.log.LogLevel;
import edu.arizona.biosemantics.common.taxonomy.TaxonIdentification;
import edu.arizona.biosemantics.matrixreview.shared.model.Model;
import edu.arizona.biosemantics.matrixreview.shared.model.core.Character;
import edu.arizona.biosemantics.matrixreview.shared.model.core.Organ;
import edu.arizona.biosemantics.matrixreview.shared.model.core.Taxon;
import edu.arizona.biosemantics.matrixreview.shared.model.core.TaxonMatrix;
import edu.arizona.biosemantics.matrixreview.shared.model.core.Value;
import edu.arizona.biosemantics.taxoncomparison.model.CharacterState;
import edu.arizona.biosemantics.taxoncomparison.model.CharacterizedTaxon;
import edu.arizona.biosemantics.taxoncomparison.model.Taxonomy;

public class MatrixReviewModelReader {

	public Taxonomy getTaxonomy(Model model) {
		List<CharacterizedTaxon> rootTaxa = new LinkedList<CharacterizedTaxon>();
		TaxonMatrix matrix = model.getTaxonMatrix();
		
		for(Taxon taxon : matrix.getHierarchyRootTaxa()) {
			CharacterizedTaxon rootTaxon = createCharacterizedTaxon(taxon, model);
			rootTaxa.add(rootTaxon);
			addChildren(rootTaxon, taxon, model);
		}		
		return new Taxonomy(rootTaxa);
	}

	private void addChildren(CharacterizedTaxon rootTaxon, Taxon taxon, Model model) {
		for(Taxon child : taxon.getChildren()) {
			CharacterizedTaxon childTaxon = createCharacterizedTaxon(child, model);
			rootTaxon.addChild(childTaxon);
			addChildren(childTaxon, child, model);
		}
	}

	private CharacterizedTaxon createCharacterizedTaxon(Taxon taxon, Model model) {		
		return new CharacterizedTaxon(taxon.getFullName(), createTaxonIdentification(taxon, model), createCharacters(taxon, model));
	}

	private TaxonIdentification createTaxonIdentification(Taxon taxon, Model model) {
		// TODO Auto-generated method stub
		return null;
	}

	private Collection<CharacterState> createCharacters(Taxon taxon, Model model) {
		Collection<CharacterState> characterStates = new LinkedList<CharacterState>();
		List<Organ> charactersByOrgan = model.getTaxonMatrix().getHierarchyCharacters();
		for(Organ organ : charactersByOrgan) {
			Set<Character> characters = organ.getCharacters();//[shape of calyx]
			for(Character character : characters){
				Value state = model.getTaxonMatrix().getValue(taxon, character);
				if(state != null && !state.getValue().isEmpty()){ //5(3-7)-parted
					CharacterState characterState = new CharacterState(organ.getName(), character.getName(), state.getValue(), 1);
					characterStates.add(characterState);
				} else {
					log(LogLevel.DEBUG, "Empty state.");
				}
			}
		}
		return characterStates;
	}

}
