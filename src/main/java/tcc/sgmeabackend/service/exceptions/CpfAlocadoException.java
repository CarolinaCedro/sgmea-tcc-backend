package tcc.sgmeabackend.service.exceptions;

public class CpfAlocadoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CpfAlocadoException(String message, Throwable cause) {
		super(message, cause);
	}

	public CpfAlocadoException(String message) {
		super(message);
	}

}
