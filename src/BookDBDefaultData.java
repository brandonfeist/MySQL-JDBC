import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BookDBDefaultData {

	final static int MAX_ROWS = 20;
	
	/**
	 * Reads data from file and returns a String array of size MAX_ROWS
	 * Will catch file not found (data doesn't exist)
	 * Will catch array index out of bounds (not enough data)
	 * @param fileName String the file to be read.
	 * @return String array of parsed fileName output
	 */
	public static String [] getDataFromFile(String fileName) {
		String [] result = new String [MAX_ROWS];
		File inputFile = new File(fileName);
		try {
			Scanner inputFileData = new Scanner(inputFile);
			
			for(int i = 0; i < MAX_ROWS; i++) {
				result[i] = inputFileData.nextLine().trim();
			}
			
			inputFileData.close();
		} catch (FileNotFoundException | ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}

		return result;
	}

}
