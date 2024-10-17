package tcc.sgmeabackend.service.exceptions;

public class EmailAlocadoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EmailAlocadoException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmailAlocadoException(String message) {
		super(message);
	}

}
