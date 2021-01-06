package validator.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class allows you to create a message with configurable values
 */
public class Message {

	/** DEFAULT_MESSAGE constant */
	public static final String DEFAULT_MESSAGE = "data is not consistent";

	/** OPEN_BRACKET constant */
	public static final char OPEN_BRACKET = '{';

	/** CLOSE_BRACKET constant */
	public static final char CLOSE_BRACKET = '}';

	/** message attribute */
	private String msg;

	/** parameters attribute */
	private Map<String, Object> parameters = new HashMap<>();

	/**
	 * constructor allowing to obtain the messages present in the annotation
	 * 
	 * @param annotation
	 * @param index
	 */
	public Message(Validate annotation, int index) {
		String[] messages = annotation.messages();
		if (index < messages.length) {
			msg = messages[index];
		} else {
			msg = DEFAULT_MESSAGE;
		}
	}

	/**
	 * Add a parameter to the message
	 * 
	 * @param key
	 * @param value
	 */
	public void parameter(String key, Object value) {
		parameters.put(key, value);
	}

	/**
	 * Get the message by replacing the parameters with the associated values
	 * 
	 * @return
	 */
	public String message() {
		StringBuilder builder = new StringBuilder(msg);
		for (Entry<String, Object> parameter : parameters.entrySet()) {
			String key = OPEN_BRACKET + parameter.getKey() + CLOSE_BRACKET;
			String value = parameter.getValue().toString();
			int index = builder.indexOf(key);
			while (index != -1) {
				builder.replace(index, index + key.length(), value);
				index += value.length(); // Move to the end of the replacement
				index = builder.indexOf(key, index);
			}
		}
		return builder.toString();
	}

}
