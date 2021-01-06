package validator.validators;

import validator.core.Validator;

public class AlphabeticValidator implements Validator<CharSequence, Alphabetic> {

	@Override
	public boolean validate(CharSequence value, Alphabetic annotation) {
		for (int i = 0; i < value.length(); i++) {
			if (!Character.isLetter(value.charAt(i))) {
				return false;
			}
		}
		return true;
	}

}
