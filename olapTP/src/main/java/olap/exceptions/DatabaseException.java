package olap.exceptions;

@SuppressWarnings("serial")
public class DatabaseException extends RuntimeException {

	public DatabaseException() {}
	
	public DatabaseException(String s) {
		super(s);
	}
}
