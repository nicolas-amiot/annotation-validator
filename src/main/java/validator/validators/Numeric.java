package validator.validators;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import validator.core.Validate;

@Retention(RetentionPolicy.RUNTIME)
@Validate(NumericValidator.class)
@NotNull
public @interface Numeric {

	String message() default "must be numeric";

}
