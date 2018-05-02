import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class WordSearchTest {

	@Test
	public void testLoadWordsFromFile() {
		WordSearch search = new WordSearch();
		search.fileName="testData/testFile.txt";
		search.loadStructuresFromFile();
		List<String> expectedWords = Arrays.asList(new String[] {"SCOUT","AVOCADO","BONE","ROPE","STICK","BIKE"});
		assertEquals(expectedWords,search.words);
	}
	
}
