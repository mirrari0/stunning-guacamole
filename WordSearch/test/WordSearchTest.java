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
	public void testCreatePointListDiagonalUpBackward() {
		List<Point> expectedPoints = new ArrayList<Point>();
		expectedPoints.add(new Point(10,9));
		expectedPoints.add(new Point(9,8));
		expectedPoints.add(new Point(8,7));
		expectedPoints.add(new Point(7,6));
		assertEquals(expectedPoints, search.createPointList(10, 7, 9, 6));	
	}
	
	
	
}
