package Data;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class DefaultBankDirectoryTest {

	@Test
	void ListsAllFilesInADirectoryWithCorrectNames() throws IOException {
		BankDirectory directory = DefaultBankDirectory.of("tests/rsc", ".JSON");
		String[] fileNames = directory.listFileNames();
		
		List<String> fileNamesList = Arrays.asList(fileNames);
		
		assertTrue(fileNamesList.contains("incorrectTargetDataForTest"));
		assertTrue(fileNamesList.contains("targetDataForTest"));
		assertEquals(3, fileNamesList.size());
	}

	@Test
	void getRepositoryOfFocusFileNameWorks() throws Exception {
		BankDirectory directory = DefaultBankDirectory.of("tests/rsc", ".JSON");
		directory.loadBankFile("targetDataForTest");
		TargetReader rep = directory.getTargetReader();
		assertEquals("Bob", rep.getNextTarget().getName());
	}

	@Test
	void focusFileNotFoundThrowException() throws IOException {
		BankDirectory directory = DefaultBankDirectory.of("tests/rsc", ".JSON");
		assertThrows(FileNotFoundException.class, ()->directory.loadBankFile("notExist"));
	}
	
	@Test
	void directoryNotFound() {
		assertThrows(IOException.class, ()->DefaultBankDirectory.of("incorrectDirectory", ".JSON"));
	}
}
