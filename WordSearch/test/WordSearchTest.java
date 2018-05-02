import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WordSearchTest {

	WordSearch search;
	
	@BeforeEach
	public void setUp() {
		search = new WordSearch();
		search.fileName="testData/testFile.txt";
		search.loadStructuresFromFile();
	}
	
	@Test
	public void testLoadWordsFromFile() {
		List<String> expectedWords = Arrays.asList(new String[] {"SCOUT","AVOCADO","BONE","ROPE","STICK","BIKE"});
		assertEquals(expectedWords,search.words);
	}
	
	
	@Test
	public void testLoadMultiDimensionalSearchArray() {
		assertEquals("A", search.searchBox[18][0]);
		assertEquals("U", search.searchBox[9][12]);
		assertEquals("P", search.searchBox[0][18]);
		assertEquals("E", search.searchBox[18][18]);
	}

	@Test
	public void testLoadStructuresFileNotFound() {
		search.fileName = "nonexistantFile";
		search.clearStructures();
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		System.setOut(new PrintStream(buffer));
		search.loadStructuresFromFile();
		System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
		assertEquals("Caught a file not found exception, searching for file: nonexistantFile\n",buffer.toString());
		buffer.reset();
	}
	
	@Test
	public void testLoadStructuresEmptyFile() {
		search.fileName = "testData/emptyFile.txt";
		search.clearStructures();
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		System.setOut(new PrintStream(buffer));
		search.loadStructuresFromFile();
		System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
		assertEquals("Caught an IO Exception while attempting to process file testData/emptyFile.txt. Error Message: File maybe empty, readline returned null\n", buffer.toString());
		buffer.reset();
	}
	
	@Test
	public void testCreatePointListWithGivenStartAndEndPointForwardHorizontal()  {
		List<Point> expectedPoints = new ArrayList<Point>();
		expectedPoints.add(new Point(9,0));
		expectedPoints.add(new Point(10,0));
		expectedPoints.add(new Point(11,0));
		expectedPoints.add(new Point(12,0));
		expectedPoints.add(new Point(13,0));
		assertEquals(expectedPoints, search.createPointList(9,13));
	}
	
	@Test
	public void testCheckIfWordIsHorizontalAndForward_NotFound() {
		assertNull(search.checkIfWordIsHorizontalAndForward(new String[] {"C","E","L","E","R","Y"}));
	}
}
