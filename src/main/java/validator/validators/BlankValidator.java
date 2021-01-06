package validator.validators;

import validator.core.Validator;

public class BlankValidator implements Validator<CharSequence, Blank> {

	@Override
	public boolean validate(CharSequence value, Blank annotation) {
		for (int i = 0; i < value.length(); i++) {
			if (!Character.isWhitespace(value.charAt(i))) {
				return false;
			}
		}
		return true;
	}

}
