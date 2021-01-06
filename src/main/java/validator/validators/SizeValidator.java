package validator.validators;

import java.util.Collection;
import java.util.Map;

import validator.core.Validator;
import validator.exception.NotSupportedTypeException;

public class SizeValidator implements Validator<Object, Size> {

	@Override
	public boolean validate(Object value, Size annotation) throws NotSupportedTypeException {
		long min = annotation.min();
		long max = annotation.max();
		int size = 0;
		if (value instanceof Collection<?>) {
			size = ((Collection<?>) value).size();
		} else if (value.getClass().isArray()) {
			size = ((Object[]) value).length;
		} else if (value instanceof Map<?, ?>) {
			size = ((Map<?, ?>) value).size();
		} else if (value instanceof CharSequence) {
			size = ((CharSequence) value).length();
		} else {
			throw new NotSupportedTypeException("Size annotation support only collection, map or char sequence");
		}
		return size >= min && size <= max;
	}

}
