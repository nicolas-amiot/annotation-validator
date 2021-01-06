package validator.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import validator.exception.NotSupportedTypeException;
import validator.exception.ValidatorException;

/**
 * Allows to validate an entity either by obtaining the 1st error or all
 */
public class ValidatorFactory {

	/**
	 * Private constructor
	 */
	private ValidatorFactory() {
	}

	/**
	 * Validate the object the object passed in parameter. All error is returned
	 * 
	 * @param object
	 * @return
	 */
	public static List<Violation> validate(Object object) {
		return validate(object, true);
	}

	/**
	 * Validate the object the object specifying if all errors should be returned
	 * 
	 * @param object
	 * @return
	 */
	public static List<Violation> validate(Object object, boolean all) {
		List<Violation> violations = new ArrayList<>();
		try {
			checkClass(object, violations, all);
			if (violations.isEmpty() && object.getClass().isAnnotationPresent(Validate.class)) {
				Class<?> clazz = object.getClass();
				Validate validate = clazz.getDeclaredAnnotation(Validate.class);
				Class<?> validator = validate.value();
				Object instance = getInstance(validator);
				boolean valid = isValid(validator, instance, object, validate);
				if (!valid) {
					List<Violation> errors = getViolations(validator, instance);
					if (errors != null && !errors.isEmpty()) {
						violations.addAll(errors);
					} else {
						violation(violations, clazz.getSimpleName(), Validate.class.getSimpleName(),
								Message.DEFAULT_MESSAGE);
					}
				}
			}
		} catch (ValidatorException | NotSupportedTypeException e) {
			e.printStackTrace();
		}
		return violations;
	}

	/**
	 * This method allows to add a violation
	 * 
	 * @param violations
	 * @param field
	 * @param annotation
	 * @param message
	 */
	public static void violation(List<Violation> violations, String field, String annotation, String message) {
		Violation violation = new Violation();
		violation.setField(field);
		violation.setAnnotation(annotation);
		violation.setMessage(message);
		violations.add(violation);
	}

	/**
	 * Check the class for getting the annotations on the attributes
	 * 
	 * @param object
	 * @param violations
	 * @param all
	 * @throws ValidatorException
	 * @throws NotSupportedTypeException
	 */
	private static void checkClass(Object object, List<Violation> violations, boolean all)
			throws ValidatorException, NotSupportedTypeException {
		Class<?> clazz = object.getClass();
		for (Field field : getAllField(clazz)) {
			boolean valid = true;
			field.setAccessible(true); // private attribute
			for (Annotation annotation : field.getDeclaredAnnotations()) {
				try {
					valid = checkField(field, annotation, field.get(object), violations, all, valid);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Check the type annotation on the specified field
	 * 
	 * @param field
	 * @param annotation
	 * @param value
	 * @param violations
	 * @param all
	 * @param valid
	 * @return
	 * @throws ValidatorException
	 * @throws NotSupportedTypeException
	 */
	private static boolean checkField(Field field, Annotation annotation, Object value, List<Violation> violations,
			boolean all, boolean valid) throws ValidatorException, NotSupportedTypeException {
		Class<?> clazz = annotation.annotationType();
		if ((all || valid) && clazz == Verify.class) {
			Collection<?> objects = toCollection(value);
			if (objects != null) {
				for (Object obj : objects) {
					checkClass(obj, violations, all);
				}
			} else {
				checkClass(value, violations, all);
			}
		} else if ((all || valid) && clazz.isAnnotationPresent(Validate.class)) {
			valid = checkAnnotation(clazz, annotation, value);
			if (valid) {
				Class<?> validator = clazz.getDeclaredAnnotation(Validate.class).value();
				valid = isValid(validator, getInstance(validator), value, annotation);
			}
			if (!valid) {
				String message = getMessage(clazz, annotation);
				violation(violations, field.getName(), clazz.getSimpleName(), message);
			}
		}
		return valid;
	}

	/**
	 * Check the annoations on the specified annotation
	 * 
	 * @param clazz
	 * @param annotation
	 * @param value
	 * @return
	 * @throws ValidatorException
	 * @throws NotSupportedTypeException
	 */
	private static boolean checkAnnotation(Class<?> clazz, Annotation annotation, Object value)
			throws ValidatorException, NotSupportedTypeException {
		boolean valid = true;
		for (Annotation note : clazz.getDeclaredAnnotations()) {
			Class<?> cls = note.annotationType();
			if (cls.isAnnotationPresent(Validate.class)) {
				valid = checkAnnotation(cls, note, value);
				if (!valid) {
					break;
				}
			}
		}
		if (valid) {
			Class<?> validator = clazz.getDeclaredAnnotation(Validate.class).value();
			valid = isValid(validator, getInstance(validator), value, annotation);
		}
		return valid;
	}

	/**
	 * Get all the fields of the class and inherited classes
	 * 
	 * @param clazz
	 * @return
	 */
	private static Field[] getAllField(Class<?> clazz) {
		List<Field> fields = new ArrayList<>();
		while (clazz != null) {
			fields.addAll(0, Arrays.asList(clazz.getDeclaredFields()));
			clazz = clazz.getSuperclass();
		}
		return fields.toArray(new Field[0]);
	}

	/**
	 * Get the collection of array, map or collection
	 * 
	 * @param object
	 * @return
	 */
	private static Collection<?> toCollection(Object object) {
		Collection<?> objects = null;
		if (object instanceof Collection<?>) {
			objects = (Collection<?>) object;
		} else if (object.getClass().isArray()) {
			objects = Arrays.asList(object);
		} else if (object instanceof Map) {
			objects = ((Map<?, ?>) object).values();
		}
		return objects;
	}

	/**
	 * Create a instance of validator
	 * 
	 * @param clazz
	 * @return
	 * @throws ValidatorException
	 */
	private static Object getInstance(Class<?> clazz) throws ValidatorException {
		Object instance = null;
		try {
			instance = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new ValidatorException("No default constructor for validator", e);
		}
		return instance;
	}

	/**
	 * Call the validate method of validator
	 * 
	 * @param clazz
	 * @param instance
	 * @param value
	 * @param annotation
	 * @return
	 * @throws ValidatorException
	 * @throws NotSupportedTypeException
	 */
	private static boolean isValid(Class<?> clazz, Object instance, Object value, Annotation annotation)
			throws ValidatorException, NotSupportedTypeException {
		boolean valid = true;
		try {
			Method method = clazz.getDeclaredMethod("validate", Object.class, Annotation.class);
			valid = (boolean) method.invoke(instance, value, annotation);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
			throw new ValidatorException("Incorrect validate method", e);
		} catch (InvocationTargetException e) {
			throw new NotSupportedTypeException("The type " + value.getClass().getSimpleName()
					+ " can't be used for the " + annotation.annotationType().getSimpleName() + " annotation", e);
		}
		return valid;
	}

	/**
	 * Get the message from annotation
	 * 
	 * @param clazz
	 * @param annotation
	 * @return
	 * @throws ValidatorException
	 */
	private static String getMessage(Class<?> clazz, Annotation annotation) throws ValidatorException {
		String message = null;
		try {
			Method method = clazz.getDeclaredMethod("message");
			message = (String) method.invoke(annotation);
			message = formatMessage(clazz, annotation, message);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new ValidatorException("The validation annotations must define a message parameter", e);
		}
		return message;
	}

	/**
	 * Get violations from class validator
	 * 
	 * @param clazz
	 * @param instance
	 * @return
	 * @throws ValidatorException
	 */
	@SuppressWarnings("unchecked")
	private static List<Violation> getViolations(Class<?> clazz, Object instance) throws ValidatorException {
		List<Violation> violations = null;
		if (instance instanceof Validation<?>) {
			try {
				Method method = clazz.getMethod("violations");
				violations = (List<Violation>) method.invoke(instance);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				throw new ValidatorException("Implementations of validation class must define a violation method", e);
			}
		}
		return violations;
	}

	/**
	 * Get the message by replacing the parameters with the associated values
	 * 
	 * @param clazz
	 * @param annotation
	 * @param message
	 * @return
	 * @throws ValidatorException
	 */
	private static String formatMessage(Class<?> clazz, Annotation annotation, String message)
			throws ValidatorException {
		StringBuilder builder = new StringBuilder();
		int current = 0;
		int start = 0;
		int end = 0;
		boolean open = false;

		while (start != -1) {
			if (!open) {
				start = message.indexOf(Message.OPEN_BRACKET, current);
				open = true;
			} else {
				end = message.indexOf(Message.CLOSE_BRACKET, start + 1);
				if (end != -1) {
					builder.append(message.substring(current, start));
					builder.append(getValue(clazz, annotation, message.substring(start + 1, end)));
					open = false;
					current = end + 1;
				} else {
					start = -1;
				}
			}
		}
		if (current < message.length()) {
			builder.append(message.substring(current, message.length()));
		}
		return builder.toString();
	}

	/**
	 * Get value from annotation
	 * 
	 * @param clazz
	 * @param annotation
	 * @param value
	 * @return
	 * @throws ValidatorException
	 */
	private static Object getValue(Class<?> clazz, Annotation annotation, String value) throws ValidatorException {
		Object object = null;
		try {
			Method method = clazz.getDeclaredMethod(value);
			object = method.invoke(annotation);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new ValidatorException("The parameter in the message could not be found in the annotation", e);
		}
		return object;
	}

}