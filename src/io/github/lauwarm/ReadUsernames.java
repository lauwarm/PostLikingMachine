/**
 * 
 */
package io.github.lauwarm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * @author Fabian
 *
 */
public class ReadUsernames {

	private static ArrayList<String> usernames = new ArrayList<String>();
	
	public static void setUsernames(String username) {
		usernames.add(username);
	}
	
	public ArrayList<String> getUsernames() {
		return usernames;
	}
	
	/**
	 * @throws IOException 
	 * 
	 */
	public ReadUsernames() throws IOException {
		// TODO Auto-generated constructor stub
		try {
			Stream<String> stream = Files.lines(Paths.get("usernames.txt"));
			stream.forEach(ReadUsernames::setUsernames);
			stream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
