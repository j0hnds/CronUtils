package com.hephaestus.cron;

/**
 * Provides the implementation of a wildcard Cron Value. This type of Cron Value
 * provides a scenario where all tested values are deemed effective. This is
 * used when the value of a particular cron component is not material to the
 * determination of effectiveness.
 * 
 * This class is package-protected and should only be constructed using the
 * CronValueFactory.
 * 
 * @author Dave Sieh
 */
class WildcardCronValue extends CronValueBase {

	/**
	 * Constructs a new WildcardCronValue with the specified limits.
	 * 
	 * @param lowerLimit
	 *            the lower limit of values acceptable for this Cron Value.
	 * @param upperLimit
	 *            the upper limit of values acceptable for this Cron Value.
	 */
	public WildcardCronValue(int lowerLimit, int upperLimit) {
		super(lowerLimit, upperLimit);
	}

	public boolean isEffective(int value) {
		// Any value is effective.
		return true;
	}

}
