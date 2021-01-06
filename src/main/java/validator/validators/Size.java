package validator.validators;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import validator.core.Validate;

@Retention(RetentionPolicy.RUNTIME)
@Validate(SizeValidator.class)
@NotNull
public @interface Size {

	String message() default "size must be between {min} and {max}";

	int min();

	int max();

}
