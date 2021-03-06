import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class WordSearchTest {

	WordSearch search;
	
	@Before
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
		assertEquals("O", search.searchBox[0][18]);
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
		assertEquals(expectedPoints, search.createPointList(9,13,0,0));
	}
	
	@Test
	public void testCreatePointListGivenStartAndEndPointsForwardHorizontal_YisNonZero() {
		List<Point> expectedPoints = new ArrayList<Point>();
		expectedPoints.add(new Point(0,12));
		expectedPoints.add(new Point(1,12));
		expectedPoints.add(new Point(2,12));
		expectedPoints.add(new Point(3,12));
		expectedPoints.add(new Point(4,12));
		assertEquals(expectedPoints, search.createPointList(0,4,12,12));
	}
	
	@Test
	public void testCheckIfWordIsHorizontalAndForward_NotFound() {
		assertNull(search.checkIfWordIsHorizontalAndForward(0, 0, new String[] {"C","E","L","E","R","Y"}));
	}
	
	@Test
	public void testCheckGridForWord_NotFound() {
		assertNull(search.checkGridForWord(new String[] {"C","E","L","E","R","Y"}));
	}
	
	@Test 
	public void testCheckIfWordIsHorizontalAndForward_WordFound() {
		assertEquals(search.createPointList(9, 13,0,0), search.checkIfWordIsHorizontalAndForward(9, 0, new String[] {"S","C","O","U","T"}));
	}
	
	@Test 
	public void testCheckGridForWord_Horizontal_Forward_WordFound() {
		assertEquals(search.createPointList(9, 13,0,0), search.checkGridForWord(new String[] {"S","C","O","U","T"}));
	}

	@Test 
	public void testCheckIfWordIsHorizontalAndForward_WordFound_nonZeroY() {
		assertEquals(search.createPointList(0, 4,12,12), search.checkIfWordIsHorizontalAndForward(0, 12, new String[] {"P","O","W","E","R"}));
	}
	
	@Test
	public void testCheckGridForWord_horizontal_forward_nonZeroY() {
		assertEquals(search.createPointList(0, 4,12,12), search.checkGridForWord(new String[] {"P","O","W","E","R"}));
	}
		
	@Test
	public void testCheckIfWordIsVerticalAndDown_notFound() {
		assertNull(search.checkIfWordIsVerticalAndDown(0,0,new String[] {"C","E","L","E","R","Y"}));
	}
	
	@Test
	public void testCreatePointListGivenStartAndEndPointsDownVertical() {
		List<Point> expectedPoints = new ArrayList<Point>();
		expectedPoints.add(new Point(0,0));
		expectedPoints.add(new Point(0,1));
		expectedPoints.add(new Point(0,2));
		expectedPoints.add(new Point(0,3));
		assertEquals(expectedPoints, search.createPointList(0, 0, 0, 3));
	}
	
	@Test
	public void testCreatePointListDownVerticalNonZeroX() {
		List<Point> expectedPoints = new ArrayList<Point>();
		expectedPoints.add(new Point(7,0));
		expectedPoints.add(new Point(7,1));
		expectedPoints.add(new Point(7,2));
		expectedPoints.add(new Point(7,3));
		assertEquals(expectedPoints, search.createPointList(7, 7, 0, 3));
	}
	
	@Test
	public void testCheckIfWordIsVerticalAndDown_found() {
		assertEquals(search.createPointList(1, 1, 0, 3), search.checkIfWordIsVerticalAndDown(1,0,new String[] {"B","O","N","E"}));
	}
	
	@Test
	public void testCreatePointListDiagonalDownForward() {
		List<Point> expectedPoints = new ArrayList<Point>();
		expectedPoints.add(new Point(7,0));
		expectedPoints.add(new Point(8,1));
		expectedPoints.add(new Point(9,2));
		expectedPoints.add(new Point(10,3));
		assertEquals(expectedPoints, search.createPointList(7, 10, 0, 3));
		
	}
	
	@Test
	public void testCheckIfWordIsDiagonalDownForward_notFound() {
		assertNull(search.checkIfWordIsDiagonalDownForward(0,0,new String[] {"C","E","L","E","R","Y"}));
	}
	
	@Test
	public void testCheckIfWordIsDiagonalDownForward_found() {
		assertEquals(search.createPointList(4, 7, 9, 13),search.checkIfWordIsDiagonalDownForward(4,9,new String[] {"B","I","K","E"}));
	}
	
	@Test
	public void testCheckGridForWord_Diagonal_Down_Forward_found() {
		assertEquals(search.createPointList(4, 7, 9, 13),search.checkGridForWord(new String[] {"B","I","K","E"}));
	}
	
	@Test
	public void testCreatePointListDiagonalUpForward() {
		List<Point> expectedPoints = new ArrayList<Point>();
		expectedPoints.add(new Point(7,3));
		expectedPoints.add(new Point(8,2));
		expectedPoints.add(new Point(9,1));
		expectedPoints.add(new Point(10,0));
		assertEquals(expectedPoints, search.createPointList(7, 10, 3, 0));	
	}
	

	@Test
	public void testCheckIfWordIsDiagonalUpForward_notFound() {
		assertNull(search.checkIfWordIsDiagonalUpForward(0,18,new String[] {"C","E","L","E","R","Y"}));
	}
	
	@Test
	public void testCheckIfWordIsDiagonalUpForward_found() {
		assertEquals(search.createPointList(2, 5, 18, 15),search.checkIfWordIsDiagonalUpForward(2,18,new String[] {"M","E","S","O"}));
	}
	
	@Test
	public void testCheckGridForWord_Diagonal_Up_Forward_found() {
		assertEquals(search.createPointList(2, 5, 18, 15),search.checkGridForWord(new String[] {"M","E","S","O"}));
	}
	
	@Test
	public void testCreatePointListHorizonalBackwards() {
		List<Point> expectedPoints = new ArrayList<Point>();
		expectedPoints.add(new Point(10,0));
		expectedPoints.add(new Point(9,0));
		expectedPoints.add(new Point(8,0));
		expectedPoints.add(new Point(7,0));
		assertEquals(expectedPoints, search.createPointList(10, 7, 0, 0));	
	}
	
	@Test 
	public void testCheckIfWordIsHorizontalAndBackward_notFound() {
		assertNull(search.checkIfWordIsHorizontalAndBackward(12, 0, new String[] {"P","O","W","E","R"}));
	}

	
	@Test 
	public void testCheckIfWordIsHorizontalAndBackward_WordFound() {
		assertEquals(search.createPointList(13, 9,0,0), search.checkIfWordIsHorizontalAndBackward(13, 0, new String[] {"T","U","O","C","S"}));
	}
	
	@Test 
	public void testCheckGridForWord_Horizontal_Backward_WordFound() {
		assertEquals(search.createPointList(13, 9,0,0), search.checkGridForWord( new String[] {"T","U","O","C","S"}));
	}
	
	@Test
	public void testCreatePointListVerticalUp() {
		List<Point> expectedPoints = new ArrayList<Point>();
		expectedPoints.add(new Point(10,9));
		expectedPoints.add(new Point(10,8));
		expectedPoints.add(new Point(10,7));
		expectedPoints.add(new Point(10,6));
		assertEquals(expectedPoints, search.createPointList(10, 10, 9, 6));	
	}

	
	@Test 
	public void testCheckIfWordIsVerticalAndUp_notFound() {
		assertNull(search.checkIfWordIsVerticalAndUp(0, 12, new String[] {"P","O","W","E","R"}));
	}

	
	@Test 
	public void testCheckIfWordIsVerticalAndUp_WordFound() {
		assertEquals(search.createPointList(12, 12,5,2), search.checkIfWordIsVerticalAndUp(12, 5, new String[] {"P","A","C","O"}));
	}

	
	@Test 
	public void testCheckGridForWord_Vertical_Up_WordFound() {
		assertEquals(search.createPointList(12, 12,5,2), search.checkGridForWord( new String[] {"P","A","C","O"}));
	}
	
	@Test
	public void testCreatePointListDiagonalDownBackward() {
		List<Point> expectedPoints = new ArrayList<Point>();
		expectedPoints.add(new Point(10,6));
		expectedPoints.add(new Point(9,7));
		expectedPoints.add(new Point(8,8));
		expectedPoints.add(new Point(7,9));
		assertEquals(expectedPoints, search.createPointList(10, 7, 6, 9));	
	}
	

	@Test
	public void testCheckIfWordIsDiagonalDownBackward_notFound() {
		assertNull(search.checkIfWordIsDiagonalDownBackward(18,0,new String[] {"C","E","L","E","R","Y"}));
	}

	
	@Test 
	public void testCheckIfWordIsDiagonalBackwardAndDown_WordFound() {
		assertEquals(search.createPointList(10, 7,12,15), search.checkIfWordIsDiagonalDownBackward(10, 12, new String[] {"S","I","N","D"}));
	}

	
	@Test 
	public void testCheckGridForWord_Diagonal_Backward_Down_WordFound() {
		assertEquals(search.createPointList(10, 7,12,15), search.checkGridForWord( new String[] {"S","I","N","D"}));
	}

	
	@Test
	public void testCreatePointListDiagonalUpBackward() {
		List<Point> expectedPoints = new ArrayList<Point>();
		expectedPoints.add(new Point(10,9));
		expectedPoints.add(new Point(9,8));
		expectedPoints.add(new Point(8,7));
		expectedPoints.add(new Point(7,6));
		assertEquals(expectedPoints, search.createPointList(10, 7, 9, 6));	
	}
	

	@Test
	public void testCheckIfWordIsDiagonalUpBackward_notFound() {
		assertNull(search.checkIfWordIsDiagonalUpBackward(18,18,new String[] {"C","E","L","E","R","Y"}));
	}
	
	@Test
	public void testCheckIfWordIsDiagonalUpBackward_found() {
		assertEquals(search.createPointList(7,4,8,5), search.checkIfWordIsDiagonalUpBackward(7, 8, new String[] {"S","P","O","N"}));
	}
	
	@Test
	public void testCheckGridForWord_Diagonal_Up_Backward_found() {
		assertEquals(search.createPointList(7,4,8,5), search.checkGridForWord(new String[] {"S","P","O","N"}));
	}
	
	@Test
	public void testStringToStringArray_null() {
		assertNull(search.parseToStringArray(null));
	}
	
	@Test
	public void testStringToStringArray() {
		assertArrayEquals( new String[] {"C","E","L","E","R","Y"}, search.parseToStringArray("CELERY"));
	}
	
	@Test
	public void testProcessExpectedWords() {
		Map<String,List<Point>> expected = new HashMap<>();
		expected.put("SCOUT",search.createPointList(9, 13, 0, 0));
		expected.put("AVOCADO", search.createPointList(12, 6, 1, 1));
		expected.put("BONE", search.createPointList(1, 1, 0, 3));
		expected.put("STICK", search.createPointList(2, 2, 6, 2));
		expected.put("BIKE", search.createPointList(4, 7, 9, 13));
		expected.put("ROPE", search.createPointList(3, 0, 10, 7));
		assertEquals(expected,search.processWordsAgainstGrid());
	}
	
	@Test
	public void testPrintOutLocations() {
		Map<String,List<Point>> expected = new HashMap<>();
		expected.put("SCOUT",search.createPointList(9, 13, 0, 0));
		expected.put("AVOCADO", search.createPointList(12, 6, 1, 1));
		expected.put("BONE", search.createPointList(1, 1, 0, 3));
		expected.put("ROPE", search.createPointList(3, 0, 10, 7));
		expected.put("STICK", search.createPointList(2, 2, 6, 2));
		expected.put("BIKE", search.createPointList(4, 7, 9, 13));
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		System.setOut(new PrintStream(buffer));
		search.printOutLocations(expected);
		System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
		StringBuffer sb = new StringBuffer();
		sb.append("SCOUT: (9,0),(10,0),(11,0),(12,0),(13,0)");
		sb.append("\n");
		sb.append("AVOCADO: (12,1),(11,1),(10,1),(9,1),(8,1),(7,1),(6,1)");
		sb.append("\n");
		sb.append("BONE: (1,0),(1,1),(1,2),(1,3)");
		sb.append("\n");
		sb.append("ROPE: (3,10),(2,9),(1,8),(0,7)");
		sb.append("\n");
		sb.append("STICK: (2,6),(2,5),(2,4),(2,3),(2,2)");
		sb.append("\n");
		sb.append("BIKE: (4,9),(5,10),(6,11),(7,12)");
		sb.append("\n");
		assertEquals(sb.toString(),buffer.toString());
	}
	
	@Test
	public void testProcessSearchWordFile() {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		System.setOut(new PrintStream(buffer));
		search.processSearchWordFile();
		System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
		StringBuffer sb = new StringBuffer();
		sb.append("SCOUT: (9,0),(10,0),(11,0),(12,0),(13,0)");
		sb.append("\n");
		sb.append("AVOCADO: (12,1),(11,1),(10,1),(9,1),(8,1),(7,1),(6,1)");
		sb.append("\n");
		sb.append("BONE: (1,0),(1,1),(1,2),(1,3)");
		sb.append("\n");
		sb.append("ROPE: (3,10),(2,9),(1,8),(0,7)");
		sb.append("\n");
		sb.append("STICK: (2,6),(2,5),(2,4),(2,3),(2,2)");
		sb.append("\n");
		sb.append("BIKE: (4,9),(5,10),(6,11),(7,12)");
		sb.append("\n");
		assertEquals(sb.toString(),buffer.toString());
		
	}
	
	@Test
	public void testParseArguments() throws Exception {
		search.clearStructures();
		search.fileName = null;
		assertNull(search.fileName);
		search.parseArgs(new String[] {"testData/testFile.txt"});
		assertEquals("testData/testFile.txt",search.fileName);
	}

	@Test
	public void testParseArgs_null() {
		search.clearStructures();
		search.fileName = null;
		try {
			search.parseArgs(null);
			fail();
		}
		catch (Exception e) {
			assertEquals("Expecting full file path to be passed in as an arguement.", e.getMessage());
			assertNull(search.fileName);
		}
	}

	@Test
	public void testParseArgs_emptyString() {
		search.clearStructures();
		search.fileName = null;
		try {
			search.parseArgs(new String[] {});
			fail();
		}
		catch (Exception e) {
			assertEquals("Expecting full file path to be passed in as an arguement.", e.getMessage());
			assertNull(search.fileName);
		}
	}
	
	@Test
	public void testIfWordIsHorizontalAndForward_partialMatchOnEdgeOfField() {
		assertNull(search.checkIfWordIsHorizontalAndForward(17, 0, search.parseToStringArray("EACH")));
	}
	
	@Test
	public void testIfWordIsHorizontalAndBackward_partialMatchOnEdgeOfField() {
		assertNull(search.checkIfWordIsHorizontalAndBackward(1, 0, search.parseToStringArray("BACON")));
	}
	
	@Test
	public void testIfWordIsVerticalAndDown_partialMatchOnEdgeOfField() {
		assertNull(search.checkIfWordIsVerticalAndDown(0, 17, search.parseToStringArray("BOOM")));
	}
	
	@Test
	public void testIfWordIsVerticalAndUp_partialMatchOnEdgeOfField() {
		assertNull(search.checkIfWordIsVerticalAndUp(11, 1, search.parseToStringArray("VOID")));
	}
	
	@Test
	public void testIfWordIsDiagonalForwardAndDown_partialMatchOnEdgeOfField() {
		assertNull(search.checkIfWordIsDiagonalDownForward(17, 7, search.parseToStringArray("NAIL")));
	}
	
	@Test
	public void testIfWordIsDiagonalForwardAndUp_partialMatchOnEdgeOfField() {
		assertNull(search.checkIfWordIsDiagonalUpForward(17, 14, search.parseToStringArray("PAIL")));
	}
	
	@Test
	public void testIfWordIsDiagonalBackwardAndDown_partialMatchOnEdgeOfField()	{
		assertNull(search.checkIfWordIsDiagonalDownBackward(1, 7, search.parseToStringArray("COIL")));
	}
	
	@Test
	public void testIfWordIsDiagonalBackwardAndUp_partialMatchOnEdgeOfField() {
		assertNull(search.checkIfWordIsDiagonalUpBackward(1, 5, search.parseToStringArray("NILE")));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
