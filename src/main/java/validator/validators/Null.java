package validator.validators;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import validator.core.Validate;

@Retention(RetentionPolicy.RUNTIME)
@Validate(NullValidator.class)
public @interface Null {

	String message() default "must be null";

}
