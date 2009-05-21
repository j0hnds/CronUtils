package com.hephaestus.cron;

/**
 * Provides the implementation of a step Cron Value. This type of Cron Value
 * allows the definition of a step value against which other values are tested
 * for effectiveness. For example, if the step value specified was 3, then any
 * value that is evenly divisible by 3 is deemed effective.
 * 
 * This class is package-protected and should only be constructed using the
 * CronValueFactory.
 * 
 * @author Dave Sieh
 */
class StepCronValue extends CronValueBase {

	// The Step value to use to determine the effectiveness of other values.
	private int stepValue;

	/**
	 * Constructs a new StepCronValue with the specified limits and step value.
	 * 
	 * @param lowerLimit
	 *            the lower limit of values acceptable for this Cron Value.
	 * @param upperLimit
	 *            the upper limit of values acceptable for this Cron Value.
	 * @param stepValue
	 *            the step value to use for determining the effectiveness of
	 *            other values.
	 */
	public StepCronValue(int lowerLimit, int upperLimit, int stepValue) {
		super(lowerLimit, upperLimit);
		
		if (stepValue == 0 || stepValue > upperLimit) {
			throw new IllegalArgumentException("Invalid step value: "
					+ stepValue);
		}
		
		this.stepValue = stepValue;
	}

	public boolean isEffective(int value) {
		// Use the mod operator to determine effectiveness.
		return value % stepValue == 0;
	}

}
