package edu.arizona.biosemantics.taxoncomparison;

import java.io.IOException;
import java.util.Properties;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import edu.arizona.biosemantics.common.log.Logger;

public class Configuration extends AbstractModule {

	private final static Logger logger = Logger.getLogger(Configuration.class);
	
	public static String projectVersion;
	public  String filePath2ReferenceMatrix;
	public  String filePath2ComparisonMatrix;
	public  String filePath2CharacterWeights;
	
	private float disjointSimMax;
	private float symDiffMax;
	private float congruenceSimMin;
	private float inclusionSimMin;
	private float asymDiffMin;

	
	
	public Configuration(){
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties properties = new Properties();
		try {
			properties.load(loader.getResourceAsStream("edu/arizona/biosemantics/taxoncomparison/config.properties"));

			filePath2ReferenceMatrix = properties.getProperty("filePath2ReferenceMatrix");
			filePath2ComparisonMatrix = properties.getProperty("filePath2ComparisonMatrix");
			filePath2CharacterWeights = properties.getProperty("filePath2CharacterWeights");
			disjointSimMax = Float.valueOf(properties.getProperty("disjointSimMax"));
			symDiffMax = Float.valueOf(properties.getProperty("symDiffMax"));
			congruenceSimMin = Float.valueOf(properties.getProperty("congruenceSimMin"));
			inclusionSimMin = Float.valueOf(properties.getProperty("inclusionSimMin"));
			asymDiffMin = Float.valueOf(properties.getProperty("asymDiffMin"));
			
			properties.load(loader.getResourceAsStream("edu/arizona/biosemantics/taxoncomparison/static.properties"));
			projectVersion = properties.getProperty("project.version");
			

		} catch (IOException e) {
			logger.error("Couldn't read configuration", e);
		}
	}
	
	@Override 
	protected void configure() {
		bind(String.class).annotatedWith(Names.named("FilePath2ReferenceMatrix")).toInstance(this.filePath2ReferenceMatrix);
		bind(String.class).annotatedWith(Names.named("FilePath2ComparisonMatrix")).toInstance(this.filePath2ComparisonMatrix);
		bind(String.class).annotatedWith(Names.named("FilePath2CharacterWeights")).toInstance(this.filePath2CharacterWeights);
		bind(Float.class).annotatedWith(Names.named("DisjointSimMax")).toInstance(this.disjointSimMax);
		bind(Float.class).annotatedWith(Names.named("SymDiffMax")).toInstance(this.symDiffMax);
		bind(Float.class).annotatedWith(Names.named("CongruenceSimMin")).toInstance(this.congruenceSimMin);
		bind(Float.class).annotatedWith(Names.named("InclusionSimMin")).toInstance(this.inclusionSimMin);
		bind(Float.class).annotatedWith(Names.named("AsymDiffMin")).toInstance(this.asymDiffMin);
		
		bind(String.class).annotatedWith(Names.named("ProjectVersion")).toInstance(this.projectVersion);
	}	


	public  String getProjectVersion() {
		return projectVersion;
	}

	public  void setProjectVersion(String projectVersion) {
		projectVersion = projectVersion;
	}

	public  String getFilePath2ReferenceMatrix() {
		return filePath2ReferenceMatrix;
	}

	public  void setFilePath2ReferenceMatrix(String filePath2ReferenceMatrix) {
		filePath2ReferenceMatrix = filePath2ReferenceMatrix;
	}

	public  String getFilePath2ComparisonMatrix() {
		return filePath2ComparisonMatrix;
	}

	public  void setFilePath2ComparisonMatrix(String filePath2ComparisonMatrix) {
		filePath2ComparisonMatrix = filePath2ComparisonMatrix;
	}

	public  Logger getLogger() {
		return logger;
	}

	
	public  String getFilePath2CharacterWeights() {
		return filePath2CharacterWeights;
	}

	public  void setFilePath2CharacterWeights(String filePath2CharacterWeights) {
		filePath2CharacterWeights = filePath2CharacterWeights;
	}


	public float getDisjointSimMax() {
		return disjointSimMax;
	}

	public void setDisjointSimMax(float disjointSimMax) {
		this.disjointSimMax = disjointSimMax;
	}

	public float getSymDiffMax() {
		return symDiffMax;
	}

	public void setSymDiffMax(float symDiffMax) {
		this.symDiffMax = symDiffMax;
	}

	public float getCongruenceSimMin() {
		return congruenceSimMin;
	}

	public void setCongruenceSimMin(float congruenceSimMin) {
		this.congruenceSimMin = congruenceSimMin;
	}

	public float getInclusionSimMin() {
		return inclusionSimMin;
	}

	public void setInclusionSimMin(float inclusionSimMin) {
		this.inclusionSimMin = inclusionSimMin;
	}

	public float getAsymDiffMin() {
		return asymDiffMin;
	}

	public void setAsymDiffMin(float asymDiffMax) {
		this.asymDiffMin = asymDiffMax;
	}

	public String toString(){
		StringBuffer sb =new StringBuffer();
		sb.append("filePath2ReferenceMatrix = "+ filePath2ReferenceMatrix+System.getProperty("line.separator"));
		sb.append("filePath2ComparisonMatrix = "+ filePath2ComparisonMatrix+System.getProperty("line.separator"));
		sb.append("filePath2CharacterWeights = "+filePath2CharacterWeights+System.getProperty("line.separator"));
		sb.append("disjointSimMax = "+disjointSimMax+System.getProperty("line.separator"));
		sb.append("congruenceSimMin = "+congruenceSimMin+System.getProperty("line.separator"));
		sb.append("inclusionSimMin = "+inclusionSimMin+System.getProperty("line.separator"));
		sb.append("symDiffMax = "+symDiffMax+System.getProperty("line.separator"));
		sb.append("asymDiffMax = "+asymDiffMin+System.getProperty("line.separator"));
		return sb.toString();
	}
	

	
}
