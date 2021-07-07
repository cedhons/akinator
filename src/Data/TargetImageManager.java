package Data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class TargetImageManager {
	
	private String imageDirectoryPath;
	
	public TargetImageManager(String imageDirectoryPath) {
		this.imageDirectoryPath = imageDirectoryPath;
	}

	public String copy(File sourceImage) {
		if(sourceImage == null) return null;
		try {
			Path source = sourceImage.toPath();
			Files.copy(source, Paths.get(imageDirectoryPath, source.getFileName().toString()), StandardCopyOption.REPLACE_EXISTING);
			return source.getFileName().toString();
		} catch (IOException e) {
			System.err.println(e);
			return null;
		}
	}

}
