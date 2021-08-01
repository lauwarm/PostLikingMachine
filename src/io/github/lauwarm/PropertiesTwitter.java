/**
 * 
 */
package io.github.lauwarm;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Fabian
 *
 */
public class PropertiesTwitter {

	/**
	 * 
	 */
	public static Properties getProperties() {
		Properties prop = new Properties();
		
		try (InputStream input = PropertiesTwitter.class.getClassLoader().getResourceAsStream("twitter4j.properties")) {
			prop.load(input);			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop;
	}
}
