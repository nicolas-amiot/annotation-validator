package validator.validators;

import validator.core.Validator;

public class NotBlankValidator implements Validator<CharSequence, NotBlank> {

	@Override
	public boolean validate(CharSequence value, NotBlank annotation) {
		for (int i = 0; i < value.length(); i++) {
			if (!Character.isWhitespace(value.charAt(i))) {
				return true;
			}
		}
		return false;
	}

}
