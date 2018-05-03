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

	public List<Point> createPointList(int xStart, int xEnd, int yStart, int yEnd) {
		List<Point> points = new ArrayList<Point>();
		if(xEnd - xStart != 0) {
			for(int i = xStart; i <= xEnd; i++) {
				points.add(new Point(i,yStart));
			}
		}
		else { 
			for(int i = yStart; i <= yEnd; i++) {
				points.add(new Point(xStart,i));
			}
		}
		
		return points;
	}

	public List<Point> checkIfWordIsHorizontalAndForward(String[] word) {
		for(int y = 0; y < searchBox.length; y++) {
			for(int x = 0; x <searchBox.length; x++) {
				if(searchBox[x][y].equals(word[0])) {
					boolean foundWord = true;
					for(int wordLoc = 1; wordLoc < word.length; wordLoc++) {
						if((x+wordLoc) >= searchBox.length || !searchBox[x+wordLoc][y].equals(word[wordLoc])) {
							foundWord = false;
							break;
						}
					}
					if(foundWord) {
						return createPointList(x, x + word.length - 1, y,y);
					}
				}
			}
		}
		return null;
	}

	public Object checkIfWordIsVerticalAndDown(String[] word) {
		for(int x = 0; x < searchBox.length; x++) {
			for(int y = 0; y <searchBox.length; y++) {
				if(searchBox[x][y].equals(word[0])) {
					boolean foundWord = true;
					for(int wordLoc = 1; wordLoc < word.length; wordLoc++) {
						if((y+wordLoc) >= searchBox.length || !searchBox[x][y+wordLoc].equals(word[wordLoc])) {
							foundWord = false;
							break;
						}
					}
					if(foundWord) {
						return createPointList(x, x, y,y+ word.length - 1);
					}
				}
			}
		}
		return null;
	}


	
	
	
	
	

}
