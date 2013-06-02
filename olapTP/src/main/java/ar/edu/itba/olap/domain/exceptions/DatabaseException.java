package ar.edu.itba.olap.domain.exceptions;

/**
 * Excepción no chequeada que se lanza cuando ocurre un error fatal en la base de datos.
 */
@SuppressWarnings("serial")
public class DatabaseException extends RuntimeException {

	private String message;
	
	public DatabaseException() {
		
	}
	
	public DatabaseException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
