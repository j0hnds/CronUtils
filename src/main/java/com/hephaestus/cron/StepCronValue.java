package com.hephaestus.cron;

public class StepCronValue implements CronValue {
	
	private int stepValue;
	
	public StepCronValue(int stepValue) {
		this.stepValue = stepValue;
	}

	public boolean isEffective(int value) {
		return value % stepValue == 0;
	}

}
