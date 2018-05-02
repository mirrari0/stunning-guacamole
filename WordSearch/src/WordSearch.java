import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class WordSearch {

	public String fileName;
	public List<String> words;

	public void loadStructuresFromFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))) {
			words = Arrays.asList(reader.readLine().split(","));
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
