package validator.validators;

import validator.core.Validator;

public class NotNullValidator implements Validator<Object, NotNull> {

	@Override
	public boolean validate(Object value, NotNull annotation) {
		return value != null;
	}

}
