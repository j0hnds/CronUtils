package com.hephaestus.cron;

public class SingleValueCronValue implements CronValue {

	private int singleValue;
	
	public SingleValueCronValue(int singleValue) {
		this.singleValue = singleValue;
	}
	public boolean isEffective(int value) {
		return singleValue == value;
	}

}
