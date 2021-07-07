package Data;

import java.io.IOException;
import java.nio.file.Path;

public class TargetRepoFactory {
	
	private TargetReader reader;
	private TargetWriter writer;
	
	public static TargetRepoFactory of(Path path) throws IOException, ParsingError {
		String fileName = path.getFileName().toString();
		switch(fileName.substring(fileName.lastIndexOf('.')).toLowerCase()) {
			case ".json" : 
				JsonTargetFileHandler rep = JsonTargetFileHandler.of(path);
				return new TargetRepoFactory(rep, rep);
			default: 
				return new TargetRepoFactory(null, null);
		}
	}
	
	private TargetRepoFactory(TargetReader reader, TargetWriter writer) {
		this.reader = reader;
		this.writer = writer;
	}

	public TargetReader getTargetReader() {
		return reader;
	}

	public TargetWriter getTargetWriter() {
		return writer;
	}	
}
