package validators;

import validator.validators.NotBlank;

public class Address extends Coordinate {

	@NotBlank
	private String postal;

	@NotBlank
	private String city;

	/**
	 * @return the postal
	 */
	public String getPostal() {
		return postal;
	}

	/**
	 * @param postal the postal to set
	 */
	public void setPostal(String postal) {
		this.postal = postal;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

}
