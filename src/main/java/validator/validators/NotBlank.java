package validator.validators;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import validator.core.Validate;

@Retention(RetentionPolicy.RUNTIME)
@Validate(NotBlankValidator.class)
@NotNull
public @interface NotBlank {

	String message() default "can't be blank";

}
