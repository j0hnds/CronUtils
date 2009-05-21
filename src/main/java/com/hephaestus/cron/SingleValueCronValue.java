package com.hephaestus.cron;

/**
 * Provides the implementation of a single Cron Value. This type of Cron Value
 * allows the definition of a single value against which tested values are
 * deemed to be effective.
 * 
 * This class is package-protected and should only be constructed using the
 * CronValueFactory.
 * 
 * @author Dave Sieh
 */
class SingleValueCronValue extends CronValueBase {

	// The single value against which other values are tested for effectiveness
	private int singleValue;

	/**
	 * Constructs a new SingleValueCronValue object with the limits and value.
	 * 
	 * @param lowerLimit
	 *            the lower limit of values acceptable for this Cron Value.
	 * @param upperLimit
	 *            the upper limit of values acceptable for this Cron Value.
	 * @param singleValue
	 *            the value against which other values are tested for
	 *            effectiveness.
	 */
	public SingleValueCronValue(int lowerLimit, int upperLimit, int singleValue) {
		super(lowerLimit, upperLimit);

		if (!isValueWithinLimits(singleValue)) {
			throw new IllegalArgumentException("Invalid value specified: "
					+ singleValue);
		}

		this.singleValue = singleValue;
	}

	public boolean isEffective(int value) {
		// Value must equal test value.
		return singleValue == value;
	}

}
