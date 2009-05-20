package com.hephaestus.cron;

public class WildcardCronValue extends CronValueBase {

	public WildcardCronValue(int lowerLimit, int upperLimit) {
		super(lowerLimit, upperLimit);
	}

	public boolean isEffective(int value) {
		return true;
	}

}
