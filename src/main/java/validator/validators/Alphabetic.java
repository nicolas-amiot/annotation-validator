package validator.validators;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import validator.core.Validate;

@Retention(RetentionPolicy.RUNTIME)
@Validate(AlphabeticValidator.class)
@NotNull
public @interface Alphabetic {

	String message() default "must be alphabetic";

}
