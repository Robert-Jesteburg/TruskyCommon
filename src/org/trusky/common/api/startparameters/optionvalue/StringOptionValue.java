package org.trusky.common.api.startparameters.optionvalue;

public class StringOptionValue extends AbstractOptionValue<String> {

	public StringOptionValue() {
		setValue(null);
	}

	public StringOptionValue(String value) {
		setValue(value);
	}

	@Override
	public boolean isEmpty() {
		return getValue() == null || getValue().isEmpty();
	}

	@Override
	public String toString() {
		return getValue().orElse("<<NULL>>");
	}

	/**
	 * WARNING: Two empty StringObjectValues are considered to be equal!
	 *
	 * @param obj May be NULL and of any type.
	 * @return TRUE/FALSE
	 */
	@Override
	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}

		if (!(obj instanceof StringOptionValue)) {
			return false;
		}

		StringOptionValue other = (StringOptionValue) obj;
		if (isEmpty() && !other.isEmpty()) {
			return false;
		}

		if (!isEmpty()) {
			return stringsAreEqual(other);
		}

		return true;
	}

	private boolean stringsAreEqual(StringOptionValue other) {

		if (getValue().isEmpty() || other.getValue()
				.isEmpty()) {
			throw new IllegalArgumentException("This method expects both string to be present.");
		}

		return getValue().get()
				.equals(other.getValue()
						.get());

	}
}
