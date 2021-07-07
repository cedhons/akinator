package Data;

import java.util.List;

import Model.Target;

public interface TargetReader {
	
	Target getNextTarget();
	List<String> getTargetNames();
	void rewind();
}
