package edu.arizona.biosemantics.taxoncomparison.comparison.lib;

import java.util.Properties;

import edu.arizona.biosemantics.common.log.Logger;

public class Configuration {

	private final static Logger logger = Logger.getLogger(Configuration.class);
	
	public static String projectVersion;

	private static Properties staticProperties;
	private static Properties properties;

	public static double disjointSimilarityMax;
	public static double congurenceSimilarityMin;
	public static double symmetricDifferenceMax;
	public static double asymmetricDifferenceMin;
	public static double inclusionSimilarityMin;
		
	static {		
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			staticProperties = new Properties(); 
			staticProperties.load(loader.getResourceAsStream("edu/arizona/biosemantics/taxoncomparison/my/static.properties"));
			projectVersion = staticProperties.getProperty("project.version");
			
			properties = new Properties(); 
			properties.load(loader.getResourceAsStream("edu/arizona/biosemantics/taxoncomparison/my/config.properties"));
			disjointSimilarityMax = Double.valueOf(properties.getProperty("disjointSimilarityMax"));
			congurenceSimilarityMin = Double.valueOf(properties.getProperty("congurenceSimilarityMin"));
			symmetricDifferenceMax = Double.valueOf(properties.getProperty("symmetricDifferenceMax"));
			asymmetricDifferenceMin = Double.valueOf(properties.getProperty("asymmetricDifferenceMin"));
			inclusionSimilarityMin = Double.valueOf(properties.getProperty("inclusionSimilarityMin"));
			
		} catch(Exception e) {
			logger.error("Couldn't read configuration", e);
		}
	}
}
