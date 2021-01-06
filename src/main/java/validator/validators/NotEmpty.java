package validator.validators;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import validator.core.Validate;

@Retention(RetentionPolicy.RUNTIME)
@Validate(NotEmptyValidator.class)
@NotNull
public @interface NotEmpty {

	String message() default "can't be empty";

}
