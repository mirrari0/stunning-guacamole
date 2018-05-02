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
	public String[][] searchBox;

	public void loadStructuresFromFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))) {
			String line = reader.readLine();
			if(line == null) throw new IOException("File maybe empty, readline returned null"); 
			words = Arrays.asList(line.split(","));
			int currentRow = 0;
			while(line != null) {
				line = reader.readLine();
				if(line != null) {
					String[] row = line.split(",");
					if(searchBox == null) {
						searchBox = new String[row.length][row.length];
					}
					for(int i = 0; i < row.length; i++) {
						searchBox[i][currentRow] = row[i];
					}
					currentRow++;
				}
			}
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
