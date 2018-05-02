import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
			System.out.println("Caught a file not found exception, searching for file: " + fileName);
		} 
		catch (IOException e) {
			System.out.println("Caught an IO Exception while attempting to process file " + fileName + ". Error Message: " + e.getMessage());
		}
	}

	public void clearStructures() {
		words = null;
		searchBox = null;
	}

	public Object createPointList(int xStart, int xEnd) {
		List<Point> points = new ArrayList<Point>();
		for(int i = xStart; i <= xEnd; i++) {
			points.add(new Point(i,0));
		}
		return points;
	}

	public List<Point> checkIfWordIsHorizontalAndForward(String[] word) {
		return null;
	}


	
	
	
	
	

}
