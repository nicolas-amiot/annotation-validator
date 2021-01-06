package validator.validators;

import validator.core.Validator;

public class NumericValidator implements Validator<CharSequence, Numeric> {

	@Override
	public boolean validate(CharSequence value, Numeric annotation) {
		for (int i = 0; i < value.length(); i++) {
			if (!Character.isDigit(value.charAt(i))) {
				return false;
			}
		}
		return true;
	}

}
