package edu.arizona.biosemantics.taxoncomparison;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.arizona.biosemantics.common.log.LogLevel;
import edu.arizona.biosemantics.matrixreview.shared.model.Model;
import edu.arizona.biosemantics.taxoncomparison.comparison.lib.RCC5SignificanceTest;
import edu.arizona.biosemantics.taxoncomparison.comparison.lib.SimilarityBasedRCC5;
import edu.arizona.biosemantics.taxoncomparison.comparison.lib.TaxonConceptComparisonMethod;
import edu.arizona.biosemantics.taxoncomparison.io.MatrixReader;
import edu.arizona.biosemantics.taxoncomparison.model.ScoreMatrix;
import edu.arizona.biosemantics.taxoncomparison.model.TaxonComparisonResult;
import edu.arizona.biosemantics.taxoncomparison.model.TaxonConcept;
import edu.arizona.biosemantics.taxoncomparison.model.TaxonConceptRepo;


public class Main {
	public Configuration config;

	
	public static void main(String[] args) throws Throwable {
		Main m = new Main();
		m.parse(args);
		m.run();	
	}
	
	/**
	 * Run the Main
	 * @throws Throwable 
	 */
	public void run() throws Throwable {
		if(config==null)
			config = new Configuration();
		log(LogLevel.DEBUG, "run using config:");
		log(LogLevel.DEBUG, config.toString());
		Injector injector = Guice.createInjector(config);
		SimilarityBasedRCC5 sRCC5 = injector.getInstance(SimilarityBasedRCC5.class);	
		
		try {
			sRCC5.compute();
		} catch (Throwable t) {
			log(LogLevel.ERROR, "Problem to execute the run", t);
			throw t;
		}
	}
	
	public void parse(String[] args) throws IOException {	
		if(args.length==0 || config!=null){
			log(LogLevel.DEBUG, "No commandline arguments received.");
			return;
		}
		
		CommandLineParser parser = new BasicParser();
		Options options = new Options();
		
		options.addOption("m", "reference matrix", true, "filepath to the reference matrix");
		options.addOption("n", "comparsion matrix", true, "filepath to the comparison matrix");
		options.addOption("w", "character weights", true, "filepath to the character weights");

		options.addOption("i", "inclusion min", true, "the minimal of similarity differences to be considered taxon inclusion relation");
		options.addOption("o", "overlap range", true, "range of similarity to be considered taxon overlap relation");
		options.addOption("d", "disjoint max", true, "the maximum similarity to be considered taxon disjoint relation");
		options.addOption("c", "congruent min", true, "the minimal similarity to be considered taxon congruent relation");
		
		options.addOption("h", "help", true, "help info");
		
		try {
		    CommandLine commandLine = parser.parse( options, args );
		    if(commandLine.hasOption("h")) {
		    	HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp( "what is this?", options );
				return;
		    }
		    
		    if(commandLine.hasOption("w")) {
		    	config.setFilePath2CharacterWeights(commandLine.getOptionValue("w"));
		    } else {
		    	log(LogLevel.ERROR, "file path to character weights is missing");
		    	throw new IllegalArgumentException();
		    }

		    if(commandLine.hasOption("m")) {
		    	config.setFilePath2ReferenceMatrix(commandLine.getOptionValue("m"));
		    } else {
		    	log(LogLevel.ERROR, "file path to the reference matrix is missing");
		    	throw new IllegalArgumentException();
		    }
		    
		    if(commandLine.hasOption("n")) {
		    	config.setFilePath2ComparisonMatrix(commandLine.getOptionValue("n"));
		    } else {
		    	log(LogLevel.ERROR, "file path to the comparison matrix is missing");
		    	throw new IllegalArgumentException();
		    }

		    if(commandLine.hasOption("i")) {
		    	config.setInclusionToleranceMin(Float.parseFloat(commandLine.getOptionValue("i")));
		    } else {
		    	log(LogLevel.ERROR, "inclusion min value is missing");
		    	throw new IllegalArgumentException();
		    }

		    if(commandLine.hasOption("o")) {
		    	String[] range = (commandLine.getOptionValue("o")).split("\\s*?-\\s*");
		    	if(range.length!=2 || Float.parseFloat(range[0])> Float.parseFloat(range[1])){
		    		log(LogLevel.ERROR, "overlap min or max is missing, or the format is incorrect (expect 'min-max')");
			    	throw new IllegalArgumentException();
		    	}else{
		    		config.setOverlapToleranceMin(Float.parseFloat(range[0]));
		    		config.setOverlapToleranceMax(Float.parseFloat(range[1]));
		    	}
		    } else {
		    	log(LogLevel.ERROR, "overlap min or max is missing, or the format is incorrect (expect 'min-max')");
		    	throw new IllegalArgumentException();
		    }
		    
		    
		    if(commandLine.hasOption("d")) {
		    	config.setDisjointToleranceMax(Float.parseFloat(commandLine.getOptionValue("d")));
		    } else {
		    	log(LogLevel.ERROR, "disjoint max value is missing");
		    	throw new IllegalArgumentException();
		    }
		    
		    if(commandLine.hasOption("c")) {
		    	config.setCongruenceToleranceMin(Float.parseFloat(commandLine.getOptionValue("c")));
		    } else {
		    	log(LogLevel.ERROR, "congruent min value is missing");
		    	throw new IllegalArgumentException();
		    }

		} catch (ParseException e) {
			log(LogLevel.ERROR, "Problem parsing parameters", e);
		}

	}
	
}
