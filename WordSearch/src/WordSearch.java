import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		if(xEnd - xStart > 0 && yEnd - yStart > 0) { //Diagonal Forward Down
			for(int i = 0; i <= xEnd-xStart; i++) {
				points.add(new Point(xStart + i,yStart + i));
			}
		}
		else if (xEnd - xStart < 0 && yEnd - yStart > 0) { // Diagonal Backward Down
			for(int i = 0; i <= xStart-xEnd; i++) {
				points.add(new Point(xStart - i,yStart + i));
			}
		}
		else if (xEnd - xStart < 0 && yEnd - yStart < 0) { // Diagonal Backward Up
			for(int i = 0; i <= xStart-xEnd; i++) {
				points.add(new Point(xStart - i,yStart - i));
			}
		}
		else if (xEnd - xStart > 0 && yEnd - yStart < 0) { //Diagonal Forward Up
			for(int i = 0; i <= xEnd-xStart; i++) {
				points.add(new Point(xStart + i,yStart - i));
			}
		}
		else if(xEnd - xStart > 0) { //Horizonal Forward
			for(int i = xStart; i <= xEnd; i++) {
				points.add(new Point(i,yStart));
			}
		}
		else if (xEnd -xStart < 0) { //Horizontal Backward
			for(int i = 0; i <= xStart - xEnd; i++) {
				points.add(new Point(xStart - i,yStart));
			}
			
		}
		else if (yEnd - yStart < 0) { // Vertical Up
			for(int i = 0; i <= yStart - yEnd; i++) {
				points.add(new Point(xStart,yStart-i));
			}
		}
		else { //default for yEnd - yStart > 0 Vertical Down
			for(int i = yStart; i <= yEnd; i++) {
				points.add(new Point(xStart,i));
			}
		}
		
		return points;
	}
	
	public List<Point> checkGridForWord(String[] word) {
		List<Point> wordLocation = null;
		for(int y = 0; y < searchBox.length; y++) {
			for(int x = 0; x <searchBox.length; x++) {
				wordLocation = checkIfWordIsHorizontalAndForward(x,y,word);
				if(wordLocation != null) return wordLocation;
				wordLocation = checkIfWordIsVerticalAndDown(x,y,word);
				if(wordLocation != null) return wordLocation;
				wordLocation = checkIfWordIsDiagonalDownForward(x,y,word);
				if(wordLocation != null) return wordLocation;
				wordLocation = checkIfWordIsDiagonalUpForward(x,y,word);
				if(wordLocation != null) return wordLocation;
				wordLocation = checkIfWordIsHorizontalAndBackward(x,y,word);
				if(wordLocation != null) return wordLocation;
				wordLocation = checkIfWordIsVerticalAndUp(x,y,word);
				if(wordLocation != null) return wordLocation;
				wordLocation = checkIfWordIsDiagonalDownBackward(x,y,word);
				if(wordLocation != null) return wordLocation;
				wordLocation = checkIfWordIsDiagonalUpBackward(x,y,word);
				if(wordLocation != null) return wordLocation;
			}
		}
		return wordLocation;
		
	}

	public List<Point> checkIfWordIsHorizontalAndForward(int x, int y, String[] word) {
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
		return null;
	}

	public List<Point> checkIfWordIsVerticalAndDown(int x, int y, String[] word) {
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
		return null;
	}

	public List<Point> checkIfWordIsDiagonalDownForward(int x, int y, String[] word) {
		if(searchBox[x][y].equals(word[0])) {
			boolean foundWord = true;
			for(int wordLoc = 1; wordLoc < word.length; wordLoc++) {
				if((y+wordLoc) >= searchBox.length || (x+wordLoc) >= searchBox.length || !searchBox[x+wordLoc][y+wordLoc].equals(word[wordLoc])) {
					foundWord = false;
					break;
				}
			}
			if(foundWord) {
				return createPointList(x, x+ word.length - 1, y,y+ word.length - 1);
			}
		}
		return null;
	}

	public List<Point> checkIfWordIsDiagonalUpForward(int x, int y, String[] word) {
		if(searchBox[x][y].equals(word[0])) {
			boolean foundWord = true;
			for(int wordLoc = 1; wordLoc < word.length; wordLoc++) {
				if((x+wordLoc) >= searchBox.length || (y-wordLoc) < 0 || !searchBox[x+wordLoc][y-wordLoc].equals(word[wordLoc])) {
					foundWord = false;
					break;
				}
			}
			if(foundWord) {
				return createPointList(x, x+ word.length - 1, y,y - word.length + 1);
			}
		}
		return null;
	}

	public List<Point> checkIfWordIsHorizontalAndBackward(int x, int y, String[] word) {
		if(searchBox[x][y].equals(word[0])) {
			boolean foundWord = true;
			for(int wordLoc = 1; wordLoc < word.length; wordLoc++) {
				if((x-wordLoc) < 0 || !searchBox[x-wordLoc][y].equals(word[wordLoc])) {
					foundWord = false;
					break;
				}
			}
			if(foundWord) {
				return createPointList(x, x - word.length + 1, y,y);
			}
		}
		return null;
	}

	public List<Point> checkIfWordIsVerticalAndUp(int x, int y, String[] word) {
		if(searchBox[x][y].equals(word[0])) {
			boolean foundWord = true;
			for(int wordLoc = 1; wordLoc < word.length; wordLoc++) {
				if((y-wordLoc) < 0 || !searchBox[x][y-wordLoc].equals(word[wordLoc])) {
					foundWord = false;
					break;
				}
			}
			if(foundWord) {
				return createPointList(x, x, y,y - word.length + 1);
			}
		}
		return null;
	}

	public List<Point> checkIfWordIsDiagonalDownBackward(int x, int y, String[] word) {
		if(searchBox[x][y].equals(word[0])) {
			boolean foundWord = true;
			for(int wordLoc = 1; wordLoc < word.length; wordLoc++) {
				if((y+wordLoc) >= searchBox.length || (x-wordLoc) < 0 || !searchBox[x-wordLoc][y+wordLoc].equals(word[wordLoc])) {
					foundWord = false;
					break;
				}
			}
			if(foundWord) {
				return createPointList(x, x - word.length + 1, y,y+ word.length - 1);
			}
		}
		return null;
	}

	public List<Point>  checkIfWordIsDiagonalUpBackward(int x, int y, String[] word) {
		if(searchBox[x][y].equals(word[0])) {
			boolean foundWord = true;
			for(int wordLoc = 1; wordLoc < word.length; wordLoc++) {
				if((y-wordLoc) < 0 || (x-wordLoc) < 0 || !searchBox[x-wordLoc][y-wordLoc].equals(word[wordLoc])) {
					foundWord = false;
					break;
				}
			}
			if(foundWord) {
				return createPointList(x, x - word.length + 1, y,y - word.length + 1);
			}
		}
		return null;
	}

	public String[] parseToStringArray(String string) {
		if(string == null) {
			return null;
		}
		String[] array = new String[string.length()];
		for(int i = 0; i < string.length(); i++) {
			array[i] = String.valueOf(string.charAt(i));
		}
		return array;
	}

	public Map<String,List<Point>> processWordsAgainstGrid() {
		Map<String,List<Point>> wordMap = new HashMap<String,List<Point>>();
		for(String word : words) {
			wordMap.put(word, checkGridForWord(parseToStringArray(word)));
		}
		return wordMap;
	}


	public void printOutLocations(Map<String, List<Point>> expected) {
		for(String word : words) {
			System.out.println(word + ": " + getPointsListString(expected.get(word)));
		}
	}
	
	private String getPointsListString(List<Point> points) {
		String out = "";
		for(Point point : points) {
			out= out + "(" + point.x + "," + point.y + "),";
		}
		out = out.substring(0,out.length() - 1);
		return out;
	}

	
	public void processSearchWordFile() {
		loadStructuresFromFile();
		printOutLocations(processWordsAgainstGrid());
	}

	public void parseArgs(String[] strings) throws Exception {
		if(strings == null) {
			throw new Exception("Expecting full file path to be passed in as an arguement.");
		}
		fileName = strings[0];
	}


	
	
	
	
	

}
