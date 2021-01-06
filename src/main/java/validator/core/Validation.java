package validator.core;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to validate an entity by storing the different messages
 *
 * @param <T>
 */
public abstract class Validation<T> implements Validator<T, Validate> {

	/** violations attribute */
	private List<Violation> violations = new ArrayList<>();

	/**
	 * The list of data consistency violations of an entity
	 * 
	 * @return
	 */
	public List<Violation> violations() {
		return violations;
	}

	/**
	 * This method allows you to add an offense from the field and a message object
	 * 
	 * @param field
	 * @param message
	 */
	protected void violate(String field, Message message) {
		ValidatorFactory.violation(violations, field, Validate.class.getSimpleName(), message.message());
	}

}
