package com.hephaestus.cron;

public class RangeCronValue implements CronValue {
	
	private int lowerLimit;
	private int upperLimit;
	
	public RangeCronValue(int lowerLimit, int upperLimit) {
		if (lowerLimit > upperLimit) {
			throw new IllegalArgumentException("Lower limit must be less than or equal to the upper limit");
		}
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
	}

	public boolean isEffective(int value) {
		return lowerLimit <= value && value <= upperLimit;
	}

}
