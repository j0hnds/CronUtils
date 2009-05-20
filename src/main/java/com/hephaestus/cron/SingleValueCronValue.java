package com.hephaestus.cron;

public class SingleValueCronValue extends CronValueBase {

	private int singleValue;

	public SingleValueCronValue(int lowerLimit, int upperLimit, int singleValue) {
		super(lowerLimit, upperLimit);
		
		if (! isValueWithinLimits(singleValue)) {
			throw new IllegalArgumentException("Invalid value specified: " + singleValue);
		}
		
		this.singleValue = singleValue;
	}

	public boolean isEffective(int value) {
		return singleValue == value;
	}

}
