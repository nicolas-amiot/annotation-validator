package validator.validators;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import validator.core.Validate;

@Retention(RetentionPolicy.RUNTIME)
@Validate(MinValidator.class)
@NotNull
public @interface Min {

	String message() default "must be equal or greater than {value}";

	double value();

}
