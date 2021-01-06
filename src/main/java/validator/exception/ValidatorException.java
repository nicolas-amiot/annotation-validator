package validator.exception;

public class ValidatorException extends Exception {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with error message
	 * 
	 * @param message
	 */
	public ValidatorException(String message) {
		super(message);
	}

	/**
	 * Constructor with error message and cause
	 * 
	 * @param message
	 * @param cause
	 */
	public ValidatorException(String message, Throwable cause) {
		super(message, cause);
	}

}
