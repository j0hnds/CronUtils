package com.hephaestus.cron;

public class StepCronValue extends CronValueBase {

	private int stepValue;

	public StepCronValue(int lowerLimit, int upperLimit, int stepValue) {
		super(lowerLimit, upperLimit);
		if (stepValue == 0 || stepValue > upperLimit) {
			throw new IllegalArgumentException("Invalid step value: "
					+ stepValue);
		}
		this.stepValue = stepValue;
	}

	public boolean isEffective(int value) {
		return value % stepValue == 0;
	}

}
