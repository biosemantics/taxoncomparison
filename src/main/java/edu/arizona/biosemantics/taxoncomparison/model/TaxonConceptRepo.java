/**
 * 
 */
package edu.arizona.biosemantics.taxoncomparison.model;

import java.util.Enumeration;
import java.util.Hashtable;

import edu.arizona.biosemantics.matrixreview.shared.model.core.Taxon;

/**
 * @author Hong Cui
 *
 * family a
 *  genus b
 *   species c
 *   
 * will result in three taxonConceptHierarchies:
 * 
 * family a =>  family a
 *  			  genus b
 *   				species c
 *   
 * genus b =>   genus b
 *   				species c
 *   
 * species c => species c
 * 			
 */
public class TaxonConceptRepo {
	Hashtable<Taxon, TaxonConcept> taxonConcepts = new Hashtable<Taxon, TaxonConcept>(); 

	/**
	 * 
	 */
	public TaxonConceptRepo() {

	}
	
	public void putTaxonConcept(TaxonConcept taxonConcept){ //a taxonconcept is a row in a matrix
		taxonConcepts.put(taxonConcept.getTaxon(), taxonConcept);
	}
	
	public TaxonConcept getTaxonConcept(Taxon taxon){
		return taxonConcepts.get(taxon);
	}
	
	public Hashtable<Taxon, TaxonConcept> getTaxonConcepts(){
		return taxonConcepts;
	}
	
	public void addTaxonConcepts(TaxonConceptRepo repo){
		Hashtable<Taxon, TaxonConcept> newConcepts = repo.getTaxonConcepts();
		Enumeration<Taxon> newTaxa = newConcepts.keys();
		while(newTaxa.hasMoreElements()){//TODO duplicate taxa?
			Taxon key = newTaxa.nextElement();
			taxonConcepts.put(key, newConcepts.get(key));
		}
	}

	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for(TaxonConcept tc: taxonConcepts.values()){
			sb.append(tc.toString()+System.getProperty("line.separator"));
		}
		return sb.toString();
	}

}
