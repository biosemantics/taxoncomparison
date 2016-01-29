package edu.arizona.biosemantics.taxoncomparison.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Taxonomy {

	public class DFSTaxaList extends ArrayList<CharacterizedTaxon> implements Serializable {
		
		private static final long serialVersionUID = 1L;

		public DFSTaxaList() {
			for (CharacterizedTaxon taxon : rootTaxa) {
				insertDepthFirst(taxon);
			}
		}

		private void insertDepthFirst(CharacterizedTaxon taxon) {
			add(taxon);
			for (CharacterizedTaxon child : taxon.getChildren())
				insertDepthFirst(child);
		}
	}
	
	private List<CharacterizedTaxon> rootTaxa;
		
	public Taxonomy(List<CharacterizedTaxon> rootTaxa) {
		super();
		this.rootTaxa = rootTaxa;
	}

	public DFSTaxaList getTaxaDFS() {
		return new DFSTaxaList();
	}
	
	public List<CharacterizedTaxon> getRootTaxa() {
		return rootTaxa;
	}
}
