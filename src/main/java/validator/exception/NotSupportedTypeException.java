package validator.exception;

import java.lang.annotation.Annotation;

/**
 * This exception is used when the type passed does not match the expected
 */
public class NotSupportedTypeException extends Exception {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with error message
	 * 
	 * @param message
	 */
	public NotSupportedTypeException(String message) {
		super(message);
	}

	/**
	 * Constructor with error message and cause
	 * 
	 * @param message
	 * @param cause
	 */
	public NotSupportedTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor with error message
	 * 
	 * @param message
	 */
	public NotSupportedTypeException(Object object, Annotation annotation) {
		super("The type " + object.getClass() + " is not supported for the annotation " + annotation.toString());
	}

}
