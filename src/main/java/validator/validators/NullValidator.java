package validator.validators;

import validator.core.Validator;

public class NullValidator implements Validator<Object, Null> {

	@Override
	public boolean validate(Object value, Null annotation) {
		return value == null;
	}

}
