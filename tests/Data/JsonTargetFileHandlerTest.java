package Data;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import FakeData.BobEpongeData;
import Model.Target;

class JsonTargetFileHandlerTest {

	private Path pathJsonTest = Paths.get("tests/rsc/targetDataForTest.json");
	private Path pathJsonSaveTest = Paths.get("tests/rsc/targetDataForSaveTest.json");
	private Path pathIncorrectJsonTest = Paths.get("tests/rsc/incorrectTargetDataForTest.json");
	private Path pathIncorrectSynthaxJsonTest = Paths.get("tests/rsc/incorrectSynthaxDataForTest.json");
	
	@Test
	void testFileExistsForTests() {
		assertTrue(Files.exists(pathJsonTest));
	}
	
	//échantillon des différents cas distingués par l'algorithme :

	@Test
	void getNextTargetReturnsMaxTwoTargets() throws IOException, ParsingError {
		TargetReader targetRep = JsonTargetFileHandler.of(pathJsonTest);
		int countTarget = 0;
		while(targetRep.getNextTarget() != null) {
			countTarget++;
		}
		assertEquals(2, countTarget);
	}
	
	@Test
	void getNextTargetCanbeRewind() throws IOException, ParsingError {
		TargetReader targetRep = JsonTargetFileHandler.of(pathJsonTest);
		assertEquals("Bob", targetRep.getNextTarget().getName());
		assertEquals("Carlo", targetRep.getNextTarget().getName());
		targetRep.rewind();
		assertEquals("Bob", targetRep.getNextTarget().getName());
		assertEquals("Carlo", targetRep.getNextTarget().getName());
	}
	
	@Test
	void addAndSaveTargetCreatesNewFile() throws IOException, ParsingError {
		TargetWriter targetRep = JsonTargetFileHandler.of(pathJsonSaveTest);
		Set<String> questions = new HashSet<String>();
		questions.add("questions");
		targetRep.add("test", null, questions);
		targetRep.saveTargets();
		assertTrue(Files.exists(pathJsonSaveTest));
		Files.delete(pathJsonSaveTest);
	}
	
	//Cas normal :
	
	@Test
	void getNextTargetReturnsTargetsWithCorrectNames() throws IOException, ParsingError {
		TargetReader targetRep = JsonTargetFileHandler.of(pathJsonTest);
		assertEquals("Bob", targetRep.getNextTarget().getName());
		assertEquals("Carlo", targetRep.getNextTarget().getName());
	}
	
	@Test
	void getNextTargetReturnsTargetsWithCorrectImages() throws IOException, ParsingError {
		TargetReader targetRep = JsonTargetFileHandler.of(pathJsonTest);
		assertEquals(null, targetRep.getNextTarget().getImage());
		assertEquals("Carlo.png", targetRep.getNextTarget().getImage());
		
	}
	
	@Test
	void getNextTargetReturnsTargetsContainsCorrectQuestions() throws IOException, ParsingError {
		TargetReader targetRep = JsonTargetFileHandler.of(pathJsonTest);
		Target bobFromFile = targetRep.getNextTarget();
		Target bob = BobEpongeData.getBob();
		for(String currentQuestion : bob) {
			assertTrue(bobFromFile.contains(currentQuestion));
		}
		Target carloFromFile = targetRep.getNextTarget();
		Target carlo = BobEpongeData.getCarlo();
		for(String currentQuestion : carlo) {
			assertTrue(carloFromFile.contains(currentQuestion));
		}
	}
	
	@Test
	void addAndSaveTargetCreatesNewFileContainingTheTarget() throws IOException, ParsingError {
		TargetWriter targetRep = JsonTargetFileHandler.of(pathJsonSaveTest);
		Set<String> questions = new HashSet<String>();
		questions.add("question1");
		questions.add("question2");
		targetRep.add("test", null, questions);
		targetRep.saveTargets();
		
		TargetReader targetReader = JsonTargetFileHandler.of(pathJsonSaveTest);
		Target target = targetReader.getNextTarget();
		assertEquals("test", target.getName());
		assertTrue(target.contains("question1"));
		assertTrue(target.contains("question2"));
		Files.delete(pathJsonSaveTest);
	}
	
	
	//Cas d'erreurs reconnues et traitées par le programme :
	
	@Test
	void throwsErrorWhenBadJsonFormat() throws IOException {
		assertThrows(ParsingError.class, ()->JsonTargetFileHandler.of(pathIncorrectJsonTest));
	}
	
	@Test
	void throwsErrorWhenJsonSynthaxIsIncorrect() throws IOException {
		assertThrows(ParsingError.class, ()->JsonTargetFileHandler.of(pathIncorrectSynthaxJsonTest));
	}
	
	//Cas limite :
	
	@Test
	void throwsErrorWhenArgumentsInAddMethodAreNull() throws IOException, ParsingError {
		TargetWriter targetRep = JsonTargetFileHandler.of(pathJsonSaveTest);
		assertThrows(IllegalArgumentException.class, ()->targetRep.add(null, null, null));
	}
}
