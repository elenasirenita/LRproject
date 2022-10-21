package dataProvider;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.io.BufferedReader;
import java.io.FileNotFoundException;


public class ConfigFileReader {
	
	private Properties properties;
	private final String propertyFilePath= "configs//configuration.properties";

	
	public ConfigFileReader(){
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("configuration.properties not found at " + propertyFilePath);
		}		
	}
	
	public String getDriverPath(){
		String driverPath = properties.getProperty("pathToChromedriver");
		if(driverPath!= null) return driverPath;
		else throw new RuntimeException("driverPath not specified in the configuration.properties file!!!");		
	}
	
	public long getImplWait() {		
		String implicitWait = properties.getProperty("implWait");
		if(implicitWait != null) return Long.parseLong(implicitWait);
		else throw new RuntimeException("implWait not specified in the Configuration.properties file.");		
	}
	
	public String getApplicationUrl() {
		String url = properties.getProperty("url");
		if(url != null) return url;
		else throw new RuntimeException("url not specified in the configuration.properties file.");
	}
}
