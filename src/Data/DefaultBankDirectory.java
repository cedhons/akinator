package Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultBankDirectory implements BankDirectory {
	private Map<String, File> files;
	private TargetReader readerRep;
	private TargetWriter writerRep;
	private final String EXTENSION;
	private String directoryPath;
	
	public static DefaultBankDirectory of(String directoryPath, String extension) throws IOException {
		try {
			List<File> files = Arrays.asList(new File(directoryPath)
					.listFiles((file) -> file.getName().toLowerCase().endsWith(extension.toLowerCase())));
			if(files == null || files.isEmpty()) throw new IOException("No files was found in the directory of the knowledge bank");
			return new DefaultBankDirectory(files, extension.toLowerCase(), directoryPath);
		} catch(NullPointerException ex) {
			throw new IOException("The directory does not exist"); 
		}
	}

	private DefaultBankDirectory(List<File> files, String extension, String directoryPath) {
		this.files = new HashMap<String, File>();
		for(File file : files) {
			this.files.put(file.getName().substring(0, file.getName().lastIndexOf('.')), file);
		}
		EXTENSION = extension;
		this.directoryPath = directoryPath;
	}

	@Override
	public String[] listFileNames() {
		return files.keySet().toArray(new String[0]);
	}

	@Override
	public TargetReader getTargetReader() {
		return readerRep;
	}
	
	@Override
	public TargetWriter getTargetWriter() {
		return writerRep;
	}

	@Override
	public void loadBankFile(String fileName) throws IOException, ParsingError {
		File chooseFile;
		if((chooseFile = files.get(fileName)) != null) {
			TargetRepoFactory repFactory = TargetRepoFactory.of(chooseFile.toPath());
			readerRep = repFactory.getTargetReader();
			writerRep = repFactory.getTargetWriter();
			return;
		}
		throw new FileNotFoundException(fileName + " was not found");
	}

	@Override
	public boolean contains(String name) {
		return files.containsKey(name);
	}

	@Override
	public void addBank(String name) {
		files.put(name, new File(directoryPath, name + EXTENSION));
	}
}
