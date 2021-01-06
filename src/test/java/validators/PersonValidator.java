package validators;

import validator.core.Message;
import validator.core.Validate;
import validator.core.Validation;

public class PersonValidator extends Validation<Person> {

	@Override
	public boolean validate(Person value, Validate annotation) {
		if (value.getFirstname().equals(value.getLastname())) {
			Message message = new Message(annotation, 0);
			message.parameter("value", value.getFirstname());
			violate("firstname", message);
			return false;
		}
		return true;
	}

}
