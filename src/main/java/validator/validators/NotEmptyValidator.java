package validator.validators;

import java.util.Collection;
import java.util.Map;

import validator.core.Validator;
import validator.exception.NotSupportedTypeException;

public class NotEmptyValidator implements Validator<Object, NotEmpty> {

	@Override
	public boolean validate(Object value, NotEmpty annotation) throws NotSupportedTypeException {
		boolean valid = false;
		if (value instanceof Collection<?>) {
			valid = !((Collection<?>) value).isEmpty();
		} else if (value.getClass().isArray()) {
			valid = ((Object[]) value).length != 0;
		} else if (value instanceof Map<?, ?>) {
			valid = !((Map<?, ?>) value).isEmpty();
		} else if (value instanceof CharSequence) {
			valid = ((CharSequence) value).length() != 0;
		} else {
			throw new NotSupportedTypeException("Size annotation support only collection, map or char sequence");
		}
		return valid;
	}

}
