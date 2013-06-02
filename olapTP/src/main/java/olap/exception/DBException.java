package olap.exception;

@SuppressWarnings("serial")
public class DBException extends RuntimeException {

	public DBException() {}
	
	public DBException(String s) {
		super(s);
	}
}
