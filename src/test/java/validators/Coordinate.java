package validators;

import validator.validators.NotBlank;

public class Coordinate {

	@NotBlank(message = "test")
	private String country;

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

}
