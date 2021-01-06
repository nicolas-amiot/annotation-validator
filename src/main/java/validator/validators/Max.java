package validator.validators;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import validator.core.Validate;

@Retention(RetentionPolicy.RUNTIME)
@Validate(MaxValidator.class)
@NotNull
public @interface Max {

	String message() default "must be equal or less than {value}";

	double value();

}
