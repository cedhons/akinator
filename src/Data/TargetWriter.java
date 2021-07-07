package Data;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public interface TargetWriter {

	void add(String name, File image, Set<String> questions);
	void saveTargets() throws IOException;
}
