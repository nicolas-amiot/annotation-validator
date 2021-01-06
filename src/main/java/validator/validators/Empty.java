package validator.validators;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import validator.core.Validate;

@Retention(RetentionPolicy.RUNTIME)
@Validate(EmptyValidator.class)
@NotNull
public @interface Empty {

	String message() default "must be empty";

}
