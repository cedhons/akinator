package Data;

public class ParsingError extends Exception {
	public ParsingError(String message,Exception ex) {
		super(message, ex);
	}
}
