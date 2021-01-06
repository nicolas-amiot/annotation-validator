package validator.validators;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import validator.core.Validate;

@Retention(RetentionPolicy.RUNTIME)
@Validate(BlankValidator.class)
@NotNull
public @interface Blank {

	String message() default "must be blank";

}
