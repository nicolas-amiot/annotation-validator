package validators;

import java.util.List;

import validator.core.Validate;
import validator.core.Verify;
import validator.validators.Alphabetic;
import validator.validators.Max;
import validator.validators.Min;
import validator.validators.NotBlank;
import validator.validators.Size;

@Validate(value = PersonValidator.class, messages = {
		"the value \"{value}\" of the name and surname must not be identical" })
public class Person {

	@NotBlank
	private String lastname;

	@NotBlank
	@Alphabetic
	private String firstname;

	@Min(value = 18, message = "the minimum age is {value} years")
	@Max(150)
	private int age;

	@Size(min = 2, max = 5)
	@Verify
	private List<Address> addresses;

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the addresses
	 */
	public List<Address> getAddresses() {
		return addresses;
	}

	/**
	 * @param addresses the addresses to set
	 */
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

}