package validators.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import validator.core.ValidatorFactory;
import validator.core.Violation;
import validators.Address;
import validators.Person;

class ValidatorFactoryTest {

	private Person createPerson() {
		Address adress = new Address();
		adress.setCountry("France");
		adress.setPostal("12345");
		adress.setCity("somewhere");
		Address adress2 = new Address();
		adress2.setCountry("France");
		adress2.setPostal("12345");
		adress2.setCity("somewhere");
		List<Address> addresses = new ArrayList<>();
		addresses.add(adress);
		addresses.add(adress2);

		Person person = new Person();
		person.setLastname("test");
		person.setFirstname("testa");
		person.setAge(20);
		person.setAddresses(addresses);

		return person;
	}

	@Test
	void validPersonTest() {
		Person person = createPerson();

		List<Violation> violations = ValidatorFactory.validate(person);
		assertTrue(violations.isEmpty());
	}

	@Test
	void invalidPersonTest() {
		Person person = createPerson();
		person.getAddresses().get(0).setCountry(null);

		List<Violation> violations = ValidatorFactory.validate(person);
		assertEquals(1, violations.size());
		assertEquals("country", violations.get(0).getField());
		assertEquals("NotBlank", violations.get(0).getAnnotation());
		assertEquals("test", violations.get(0).getMessage());
	}

	@Test
	void invalidClassPersonTest() {
		Person person = createPerson();
		person.setFirstname("test");

		List<Violation> violations = ValidatorFactory.validate(person);
		assertEquals(1, violations.size());
		assertEquals("firstname", violations.get(0).getField());
		assertEquals("Validate", violations.get(0).getAnnotation());
		assertEquals("the value \"test\" of the name and surname must not be identical",
				violations.get(0).getMessage());
	}

	@Test
	void formatMessageTest() {
		Person person = createPerson();
		person.setAge(17);

		List<Violation> violations = ValidatorFactory.validate(person);
		assertEquals(1, violations.size());
		assertEquals("age", violations.get(0).getField());
		assertEquals("Min", violations.get(0).getAnnotation());
		assertEquals("the minimum age is " + 18d + " years", violations.get(0).getMessage());
	}

	@Test
	void multipleAnnotationTest() {
		Person person = createPerson();
		person.getAddresses().remove(1);
		person.getAddresses().get(0).setCity(null);

		List<Violation> violations = ValidatorFactory.validate(person);
		assertEquals(2, violations.size());
		assertEquals("addresses", violations.get(0).getField());
		assertEquals("Size", violations.get(0).getAnnotation());
		assertEquals("city", violations.get(1).getField());
		assertEquals("NotBlank", violations.get(1).getAnnotation());
	}

	@Test
	void multipleAnnotationFirstTest() {
		Person person = createPerson();
		person.getAddresses().remove(1);
		person.getAddresses().get(0).setCity(null);

		List<Violation> violations = ValidatorFactory.validate(person, false);
		assertEquals(1, violations.size());
		assertEquals("addresses", violations.get(0).getField());
		assertEquals("Size", violations.get(0).getAnnotation());
	}

}
