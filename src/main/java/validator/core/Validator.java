package validator.core;

import java.lang.annotation.Annotation;

import validator.exception.NotSupportedTypeException;

/**
 * The interface that any validator must implement
 */
public interface Validator<T, A extends Annotation> {

	/**
	 * The main method for validating the data
	 * 
	 * @param value
	 * @param annotation
	 * @return
	 * @throws NotSupportedTypeException
	 */
	boolean validate(T value, A annotation) throws NotSupportedTypeException;

}
