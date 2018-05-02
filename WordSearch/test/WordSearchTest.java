import static org.junit.Assert.assertEquals;

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
	
}
