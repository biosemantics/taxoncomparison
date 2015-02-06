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
	
	public float inclusionToleranceMin; // > 70% similarity difference = inclusion
	
	public float overlapToleranceMin; //30-60% similarity = overlap
	public float overlapToleranceMax; //30-60% similarity = overlap
	
	public float congruenceToleranceMin; // > 90% similarity = congruent
	public float disjointToleranceMax; //<10% similarity = disjoint
	
	
	
	public Configuration(){
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties properties = new Properties();
		try {
			properties.load(loader.getResourceAsStream("edu/arizona/biosemantics/taxoncomparison/config.properties"));

			filePath2ReferenceMatrix = properties.getProperty("filePath2ReferenceMatrix");
			filePath2ComparisonMatrix = properties.getProperty("filePath2ComparisonMatrix");
			filePath2CharacterWeights = properties.getProperty("filePath2CharacterWeights");
			inclusionToleranceMin = Float.valueOf(properties.getProperty("inclusionToleranceMin"));
			overlapToleranceMin = Float.valueOf(properties.getProperty("overlapToleranceMin"));
			congruenceToleranceMin = Float.valueOf(properties.getProperty("congruenceToleranceMin"));
			

			overlapToleranceMax = Float.valueOf(properties.getProperty("overlapToleranceMax"));
			disjointToleranceMax = Float.valueOf(properties.getProperty("disjointToleranceMax"));
			
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
		bind(Float.class).annotatedWith(Names.named("InclusionToleranceMin")).toInstance(this.inclusionToleranceMin);
		bind(Float.class).annotatedWith(Names.named("OverlapToleranceMin")).toInstance(this.overlapToleranceMin);
		bind(Float.class).annotatedWith(Names.named("CongruenceToleranceMin")).toInstance(this.congruenceToleranceMin);
		bind(Float.class).annotatedWith(Names.named("OverlapToleranceMax")).toInstance(this.overlapToleranceMax);
		bind(Float.class).annotatedWith(Names.named("DisjointToleranceMax")).toInstance(this.disjointToleranceMax);
		
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

	public float getInclusionToleranceMin() {
		return inclusionToleranceMin;
	}

	public void setInclusionToleranceMin(float inclusionToleranceMin) {
		this.inclusionToleranceMin = inclusionToleranceMin;
	}

	public float getOverlapToleranceMin() {
		return overlapToleranceMin;
	}

	public void setOverlapToleranceMin(float overlapToleranceMin) {
		this.overlapToleranceMin = overlapToleranceMin;
	}

	public float getOverlapToleranceMax() {
		return overlapToleranceMax;
	}

	public void setOverlapToleranceMax(float overlapToleranceMax) {
		this.overlapToleranceMax = overlapToleranceMax;
	}

	public float getCongruenceToleranceMin() {
		return congruenceToleranceMin;
	}

	public void setCongruenceToleranceMin(float congruenceToleranceMin) {
		this.congruenceToleranceMin = congruenceToleranceMin;
	}

	public float getDisjointToleranceMax() {
		return disjointToleranceMax;
	}

	public void setDisjointToleranceMax(float disjointToleranceMax) {
		this.disjointToleranceMax = disjointToleranceMax;
	}

	public String toString(){
		StringBuffer sb =new StringBuffer();
		sb.append("filePath2ReferenceMatrix = "+ filePath2ReferenceMatrix+System.getProperty("line.separator"));
		sb.append("filePath2ComparisonMatrix = "+ filePath2ComparisonMatrix+System.getProperty("line.separator"));
		sb.append("filePath2CharacterWeights = "+filePath2CharacterWeights+System.getProperty("line.separator"));
		sb.append("inclusionToleranceMin = "+inclusionToleranceMin+System.getProperty("line.separator"));
		sb.append("overlapToleranceMin = "+overlapToleranceMin+System.getProperty("line.separator"));
		sb.append("congruenceToleranceMin = "+congruenceToleranceMin+System.getProperty("line.separator"));
		sb.append("overlapToleranceMax = "+overlapToleranceMax+System.getProperty("line.separator"));
		sb.append("disjointToleranceMax = "+disjointToleranceMax+System.getProperty("line.separator"));
		return sb.toString();
	}

	
}
