package validator.validators;

import validator.core.Validator;

public class MinValidator implements Validator<Number, Min> {

	@Override
	public boolean validate(Number value, Min annotation) {
		double min = annotation.value();
		return value.doubleValue() >= min;
	}

}
