package com.hephaestus.cron;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CronValueFactory {

	private static Pattern SINGLE_VALUE_PATTERN = Pattern.compile("^([0-9]+)$");
	private static Pattern RANGE_PATTERN = Pattern.compile("^([0-9]+)-([0-9]+)$");
	private static Pattern STEP_PATTERN = Pattern.compile("^\\*/([0-9]+)$");

	private static final int MINUTE_LOWER_LIMIT = 0;
	private static final int MINUTE_UPPER_LIMIT = 59;
	private static final int HOUR_LOWER_LIMIT = 0;
	private static final int HOUR_UPPER_LIMIT = 23;
	private static final int DAY_LOWER_LIMIT = 1;
	private static final int DAY_UPPER_LIMIT = 31;
	private static final int MONTH_LOWER_LIMIT = 1;
	private static final int MONTH_UPPER_LIMIT = 12;
	private static final int DOW_LOWER_LIMIT = 0;
	private static final int DOW_UPPER_LIMIT = 6;
	
	private static CronValueCreator minuteCreator = null;
	private static CronValueCreator hourCreator = null;
	private static CronValueCreator dayCreator = null;
	private static CronValueCreator monthCreator = null;
	private static CronValueCreator dowCreator = null;
	
	public static interface CronValueCreator {
		CronValue createCronValue(String valueSpec);
	}
	
	private static class MinuteCronValueCreator implements CronValueCreator {

		public CronValue createCronValue(String valueSpec) {
			return parseCronValue(MINUTE_LOWER_LIMIT, MINUTE_UPPER_LIMIT, valueSpec);
		}
		
	}
	
	private static class HourCronValueCreator implements CronValueCreator {

		public CronValue createCronValue(String valueSpec) {
			return parseCronValue(HOUR_LOWER_LIMIT, HOUR_UPPER_LIMIT, valueSpec);
		}
		
	}
	
	private static class DayCronValueCreator implements CronValueCreator {

		public CronValue createCronValue(String valueSpec) {
			return parseCronValue(DAY_LOWER_LIMIT, DAY_UPPER_LIMIT, valueSpec);
		}
		
	}
	
	private static class MonthCronValueCreator implements CronValueCreator {

		public CronValue createCronValue(String valueSpec) {
			return parseCronValue(MONTH_LOWER_LIMIT, MONTH_UPPER_LIMIT, valueSpec);
		}
		
	}
	
	private static class DOWCronValueCreator implements CronValueCreator {

		public CronValue createCronValue(String valueSpec) {
			return parseCronValue(DOW_LOWER_LIMIT, DOW_UPPER_LIMIT, valueSpec);
		}
		
	}
	
	public static CronValueCreator getMinuteCreator() {
		if (minuteCreator == null) {
			minuteCreator = new MinuteCronValueCreator();
		}
		return minuteCreator;
	}
	
	public static CronValueCreator getHourCreator() {
		if (hourCreator == null) {
			hourCreator = new HourCronValueCreator();
		}
		return hourCreator;
	}
	
	public static CronValueCreator getDayCreator() {
		if (dayCreator == null) {
			dayCreator = new DayCronValueCreator();
		}
		return dayCreator;
	}
	
	public static CronValueCreator getMonthCreator() {
		if (monthCreator == null) {
			monthCreator = new MonthCronValueCreator();
		}
		return monthCreator;
	}
	
	public static CronValueCreator getDOWCreator() {
		if (dowCreator == null) {
			dowCreator = new DOWCronValueCreator();
		}
		return dowCreator;
	}
	
	private static CronValue parseCronValue(int lowerLimit, int upperLimit, String valueSpec) {
		CronValue cronValue = null;
		
		if (valueSpec == null) {
			throw new IllegalArgumentException("Must provide a non-null value specification");
		}
		
		if ("*".equals(valueSpec)) {
			cronValue = new WildcardCronValue(lowerLimit, upperLimit);
		} else {
			Matcher m = SINGLE_VALUE_PATTERN.matcher(valueSpec);
			if (m.matches()) {
				int singleValue;
				try {
					singleValue = Integer.parseInt(m.group(1));
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Invalid value specified: " + valueSpec, e);
				}
				cronValue = new SingleValueCronValue(lowerLimit, upperLimit, singleValue);
			} else {
				m = RANGE_PATTERN.matcher(valueSpec);
				if (m.matches()) {
					int rangeLower;
					int rangeUpper;
					try {
						rangeLower = Integer.parseInt(m.group(1));
						rangeUpper = Integer.parseInt(m.group(2));
					} catch (NumberFormatException e) {
						throw new IllegalArgumentException("Invalid range value: " + valueSpec, e);
					}
					cronValue = new RangeCronValue(lowerLimit, upperLimit, rangeLower, rangeUpper);
				} else {
					m = STEP_PATTERN.matcher(valueSpec);
					if (m.matches()) {
						int stepValue;
						try {
							stepValue = Integer.parseInt(m.group(1));
						} catch (NumberFormatException e) {
							throw new IllegalArgumentException("Invalid step value: " + valueSpec, e);
						}
						cronValue = new StepCronValue(lowerLimit, upperLimit, stepValue);
					} else {
						throw new IllegalArgumentException("Invalid value specified: " + valueSpec);
					}
				}
			}
		}
		
		return cronValue;
	}
}
