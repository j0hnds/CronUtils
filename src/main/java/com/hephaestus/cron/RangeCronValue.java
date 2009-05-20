package com.hephaestus.cron;

public class RangeCronValue extends CronValueBase {

	private int rangeLower;
	private int rangeUpper;

	public RangeCronValue(int lowerLimit, int upperLimit, int rangeLower,
			int rangeUpper) {
		super(lowerLimit, upperLimit);

		if (!isValueWithinLimits(rangeLower)) {
			throw new IllegalArgumentException("Invalid lower range value: "
					+ rangeLower);
		}
		if (!isValueWithinLimits(rangeUpper)) {
			throw new IllegalArgumentException("Invalid lower range value: "
					+ rangeUpper);
		}
		if (rangeLower > rangeUpper) {
			throw new IllegalArgumentException(
					"Lower limit must be less than or equal to the upper limit");
		}
		
		this.rangeLower = rangeLower;
		this.rangeUpper = rangeUpper;
	}

	public boolean isEffective(int value) {
		return rangeLower <= value && value <= rangeUpper;
	}

}
