package validator.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation allows you to specify the validator to use
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Validate {

	/** The validator associated with the annotation */
	Class<? extends Validator<?, ?>> value();

	/** The list of messages for the validation of the main class */
	String[] messages() default {};

}
