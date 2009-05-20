package com.hephaestus.cron;

public abstract class CronValueBase implements CronValue {

	private int lowerLimit;
	private int upperLimit;
	
	public CronValueBase(int lowerLimit, int upperLimit) {
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
	}
	
	public boolean isValueWithinLimits(int value) {
		return value >= lowerLimit && value <= upperLimit;
	}
}
