package validator.validators;

import validator.core.Validator;

public class MaxValidator implements Validator<Number, Max> {

	@Override
	public boolean validate(Number value, Max annotation) {
		double max = annotation.value();
		return value.doubleValue() <= max;
	}

}
