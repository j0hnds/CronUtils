package com.hephaestus.cron;
/*
 * Copyright (c) 2009 Dave Sieh
 *
 * This file is part of CronUtils.
 *
 * CronUtils is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CronUtils is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CronUtils.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A factory class for the construction of CronValue objects.
 * 
 * @author Dave Sieh
 */
public class CronValueFactory {

	// The array of days of the week. Using an array here so we can map
	// the name of the day of week to its index number.
	private static final String[] DOWS = { "sun", "mon", "tue", "wed", "thu",
			"fri", "sat" };

	private static final String[] MONTHS = { "", "jan", "feb", "mar", "apr",
			"may", "jun", "jul", "aug", "sep", "oct", "nov", "dec" };
	
	// The part of the regular expression to match the days of the week.
	private static final String DOW_EXPRESSION = "sun|mon|tue|wed|thu|fri|sat";

	// The part of the regular expression to match the months.
	private static final String MONTH_EXPRESSION = "jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec";

	// Wildcard value
	private static final String WILDCARD_PATTERN = "*";

	// The regular expression pattern identifying a cron value that is just
	// a single integer value.
	private static Pattern SINGLE_VALUE_PATTERN = Pattern.compile("^([0-9]+)$");
	private static Pattern DOW_SINGLE_VALUE_PATTERN = Pattern.compile("^("
			+ DOW_EXPRESSION + "|[0-9])$");
	private static Pattern MONTH_SINGLE_VALUE_PATTERN = Pattern.compile("^("
			+ MONTH_EXPRESSION + "|[0-9]+)$");

	// The regular expression pattern identifying a cron range value.
	private static Pattern RANGE_PATTERN = Pattern
			.compile("^([0-9]+)-([0-9]+)$");
	private static Pattern DOW_RANGE_PATTERN = Pattern.compile("^("
			+ DOW_EXPRESSION + "|[0-9])-(" + DOW_EXPRESSION + "|[0-9])$");
	private static Pattern MONTH_RANGE_PATTERN = Pattern.compile("^("
			+ MONTH_EXPRESSION + "|[0-9]+)-(" + MONTH_EXPRESSION + "|[0-9]+)$");

	// The regular expression pattern identifying a cron step value.
	private static Pattern STEP_PATTERN = Pattern.compile("^\\*/([0-9]+)$");

	// The regular expression pattern to match a textual day of week value
	private static Pattern DOW_PATTERN = Pattern.compile("^(" + DOW_EXPRESSION
			+ ")$");

	// The regular expression pattern to match a textual day of week value
	private static Pattern MONTH_PATTERN = Pattern.compile("^(" + MONTH_EXPRESSION
			+ ")$");

	// Constants defining the lower/upper limits for the different time types.
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

	// Static singleton members to hold the various CronValueCreator objects.
	private static CronValueCreator minuteCreator = null;
	private static CronValueCreator hourCreator = null;
	private static CronValueCreator dayCreator = null;
	private static CronValueCreator monthCreator = null;
	private static CronValueCreator dowCreator = null;

	/**
	 * The interface definition of a Cron Value Creator. A Cron Value Creator
	 * knows how to construct an appropriate Cron Value for a particular
	 * situation.
	 * 
	 * @author Dave Sieh
	 */
	public static interface CronValueCreator {

		/**
		 * Creates a new CronValue object initialized from the specified value
		 * specification.
		 * 
		 * @param valueSpec
		 *            the specification of the value to be constructed. For
		 *            example, might be a single value, range, wildcard or step
		 *            specification.
		 * 
		 * @return reference to an initialized CronValue appropriate for the
		 *         specification.
		 */
		CronValue createCronValue(String valueSpec);
	}

	/**
	 * A CronValueCreator specific for creating CronValue's for minutes.
	 * 
	 * @author Dave Sieh
	 */
	private static class MinuteCronValueCreator implements CronValueCreator {

		public CronValue createCronValue(String valueSpec) {
			return parseCronValue(MINUTE_LOWER_LIMIT, MINUTE_UPPER_LIMIT,
					valueSpec);
		}

	}

	/**
	 * A CronValueCreator specific for creating CronValue's for hours.
	 * 
	 * @author Dave Sieh
	 */
	private static class HourCronValueCreator implements CronValueCreator {

		public CronValue createCronValue(String valueSpec) {
			return parseCronValue(HOUR_LOWER_LIMIT, HOUR_UPPER_LIMIT, valueSpec);
		}

	}

	/**
	 * A CronValueCreator specific for creating CronValue's for days.
	 * 
	 * @author Dave Sieh
	 */
	private static class DayCronValueCreator implements CronValueCreator {

		public CronValue createCronValue(String valueSpec) {
			return parseCronValue(DAY_LOWER_LIMIT, DAY_UPPER_LIMIT, valueSpec);
		}

	}

	/**
	 * A CronValueCreator specific for creating CronValue's for months.
	 * 
	 * @author Dave Sieh
	 */
	private static class MonthCronValueCreator implements CronValueCreator {

		public CronValue createCronValue(String valueSpec) {
			return parseMonthCronValue(MONTH_LOWER_LIMIT, MONTH_UPPER_LIMIT,
					valueSpec);
		}

	}

	/**
	 * A CronValueCreator specific for creating CronValue's for days of the week
	 * 
	 * @author Dave Sieh
	 */
	private static class DOWCronValueCreator implements CronValueCreator {

		public CronValue createCronValue(String valueSpec) {
			return parseDOWCronValue(DOW_LOWER_LIMIT, DOW_UPPER_LIMIT,
					valueSpec);
		}

	}

	/**
	 * Returns the CronValueCreator to use for minutes.
	 * 
	 * @return reference to a CronValue specific for minutes.
	 */
	public static CronValueCreator getMinuteCreator() {
		if (minuteCreator == null) {
			minuteCreator = new MinuteCronValueCreator();
		}
		return minuteCreator;
	}

	/**
	 * Returns the CronValueCreator to use for hours.
	 * 
	 * @return reference to a CronValue specific for hours.
	 */
	public static CronValueCreator getHourCreator() {
		if (hourCreator == null) {
			hourCreator = new HourCronValueCreator();
		}
		return hourCreator;
	}

	/**
	 * Returns the CronValueCreator to use for days.
	 * 
	 * @return reference to a CronValue specific for days.
	 */
	public static CronValueCreator getDayCreator() {
		if (dayCreator == null) {
			dayCreator = new DayCronValueCreator();
		}
		return dayCreator;
	}

	/**
	 * Returns the CronValueCreator to use for months.
	 * 
	 * @return reference to a CronValue specific for months.
	 */
	public static CronValueCreator getMonthCreator() {
		if (monthCreator == null) {
			monthCreator = new MonthCronValueCreator();
		}
		return monthCreator;
	}

	/**
	 * Returns the CronValueCreator to use for days of week.
	 * 
	 * @return reference to a CronValue specific for days of week.
	 */
	public static CronValueCreator getDOWCreator() {
		if (dowCreator == null) {
			dowCreator = new DOWCronValueCreator();
		}
		return dowCreator;
	}

	/**
	 * Given the day of week abbreviation, return the equivalent DOW number.
	 * 
	 * @param dow
	 *            the day of week abbreviation.
	 * 
	 * @return the DOW number; -1 if an invalid day of week abbreviation is
	 *         specified.
	 */
	private static int findDOW(String dow) {
		int index = -1;

		for (int i = 0; i < DOWS.length && index < 0; i++) {
			if (DOWS[i].equals(dow)) {
				index = i;
			}
		}

		return index;
	}

	/**
	 * Given the month abbreviation, return the equivalent month number.
	 * 
	 * @param month
	 *            the month abbreviation
	 * 
	 * @return the month number; -1 if an invalid month abbreviation is
	 *         specified.
	 */
	private static int findMonth(String month) {
		int index = -1;

		for (int i = 0; i < MONTHS.length && index < 0; i++) {
			if (MONTHS[i].equals(month)) {
				index = i;
			}
		}

		return index;
	}
	
	/**
	 * Parses the specified value to return the day of week value converting as
	 * necessary.
	 * 
	 * @param month
	 *            the month specification.
	 * 
	 * @return the day of week number.
	 */
	private static int getMonth(String month) {
		int value = -1;

		if (MONTH_PATTERN.matcher(month).matches()) {
			value = findMonth(month);
			if (value < 0) {
				throw new IllegalArgumentException("Invalid value specified: "
						+ month);
			}
		} else {
			try {
				value = Integer.parseInt(month);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid value specified: "
						+ month, e);
			}
		}

		return value;
	}

	/**
	 * Parses the specified value to return the day of week value converting as
	 * necessary.
	 * 
	 * @param dow
	 *            the day of week specification.
	 * 
	 * @return the day of week number.
	 */
	private static int getDOW(String dow) {
		int value = -1;

		if (DOW_PATTERN.matcher(dow).matches()) {
			value = findDOW(dow);
			if (value < 0) {
				throw new IllegalArgumentException("Invalid value specified: "
						+ dow);
			}
		} else {
			try {
				value = Integer.parseInt(dow);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid value specified: "
						+ dow, e);
			}

			if (value == 7) {
				// Convert Sunday from 7 to 0.
				value = 0;
			}
		}

		return value;
	}

	/**
	 * This method performs the work of identifying the type of CronValue to
	 * create (wildcard, step, range, single value) by inspecting the value
	 * specification provided.
	 * 
	 * Where appropriate, the values in the value specification will be checked
	 * against the limits provided.
	 * 
	 * Runtime Exceptions may be thrown if the parser is unable to make sense of
	 * the value specification or if values lie outside the specified limits.
	 * 
	 * @param lowerLimit
	 *            the value lower limit of the Cron Value to create
	 * @param upperLimit
	 *            the value upper limit of the Cron Value to create
	 * @param dowSpecification
	 *            true if parsing a day of week specification.
	 * @param valueSpec
	 *            the specification of the value extracted from the cron
	 *            specification.
	 * 
	 * @return a reference to a newly constructed CronValue object initialized
	 *         as appropriate for the limits and value specification.
	 */
	private static CronValue parseCronValue(int lowerLimit, int upperLimit,
			String valueSpec) {

		// The CronValue to be returned.
		CronValue cronValue = null;
		// The matcher to use.
		Matcher m = null;

		if (valueSpec == null) {
			throw new IllegalArgumentException(
					"Must provide a non-null value specification");
		}

		if (WILDCARD_PATTERN.equals(valueSpec)) {

			// This is a wildcard specification (*)
			cronValue = new WildcardCronValue(lowerLimit, upperLimit);

		} else if ((m = SINGLE_VALUE_PATTERN.matcher(valueSpec)).matches()) {

			// This is a single value specification (just an integer).
			try {
				int singleValue = Integer.parseInt(m.group(1));
				cronValue = new SingleValueCronValue(lowerLimit, upperLimit,
						singleValue);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid value specified: "
						+ valueSpec, e);
			}

		} else if ((m = RANGE_PATTERN.matcher(valueSpec)).matches()) {

			// This is a range value specification (e.g. 5-10)
			try {
				int rangeLower = Integer.parseInt(m.group(1));
				int rangeUpper = Integer.parseInt(m.group(2));
				cronValue = new RangeCronValue(lowerLimit, upperLimit,
						rangeLower, rangeUpper);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid range value: "
						+ valueSpec, e);
			}

		} else if ((m = STEP_PATTERN.matcher(valueSpec)).matches()) {

			// This is a step pattern (e.g. */3 - every third time period)
			try {
				int stepValue = Integer.parseInt(m.group(1));
				cronValue = new StepCronValue(lowerLimit, upperLimit, stepValue);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid step value: "
						+ valueSpec, e);
			}

		} else {

			// Unrecognized value pattern.
			throw new IllegalArgumentException("Invalid value specified: "
					+ valueSpec);

		}

		return cronValue;
	}

	/**
	 * This method performs the work of identifying the type of CronValue to
	 * create (wildcard, step, range, single value) by inspecting the value
	 * specification provided.
	 * 
	 * Where appropriate, the values in the value specification will be checked
	 * against the limits provided.
	 * 
	 * Runtime Exceptions may be thrown if the parser is unable to make sense of
	 * the value specification or if values lie outside the specified limits.
	 * 
	 * @param lowerLimit
	 *            the value lower limit of the Cron Value to create
	 * @param upperLimit
	 *            the value upper limit of the Cron Value to create
	 * @param dowSpecification
	 *            true if parsing a day of week specification.
	 * @param valueSpec
	 *            the specification of the value extracted from the cron
	 *            specification.
	 * 
	 * @return a reference to a newly constructed CronValue object initialized
	 *         as appropriate for the limits and value specification.
	 */
	private static CronValue parseDOWCronValue(int lowerLimit, int upperLimit,
			String valueSpec) {

		// The CronValue to be returned.
		CronValue cronValue = null;
		// The matcher to use.
		Matcher m = null;

		if (valueSpec == null) {
			throw new IllegalArgumentException(
					"Must provide a non-null value specification");
		}

		if (WILDCARD_PATTERN.equals(valueSpec)) {

			// This is a wildcard specification (*)
			cronValue = new WildcardCronValue(lowerLimit, upperLimit);

		} else if ((m = DOW_SINGLE_VALUE_PATTERN.matcher(valueSpec)).matches()) {

			// This is a single value specification for a day of the week.
			int dowNumber = getDOW(m.group(1));
			cronValue = new SingleValueCronValue(lowerLimit, upperLimit,
					dowNumber);

		} else if ((m = DOW_RANGE_PATTERN.matcher(valueSpec)).matches()) {

			// This is a range value specification for day of week
			int rangeLower = getDOW(m.group(1));
			int rangeUpper = getDOW(m.group(2));
			cronValue = new RangeCronValue(lowerLimit, upperLimit, rangeLower,
					rangeUpper);

		} else if ((m = STEP_PATTERN.matcher(valueSpec)).matches()) {

			// This is a step pattern (e.g. */3 - every third time period)
			try {
				int stepValue = Integer.parseInt(m.group(1));
				cronValue = new StepCronValue(lowerLimit, upperLimit, stepValue);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid step value: "
						+ valueSpec, e);
			}

		} else {

			// Unrecognized value pattern.
			throw new IllegalArgumentException("Invalid value specified: "
					+ valueSpec);

		}

		return cronValue;
	}
	/**
	 * This method performs the work of identifying the type of CronValue to
	 * create (wildcard, step, range, single value) by inspecting the value
	 * specification provided.
	 * 
	 * Where appropriate, the values in the value specification will be checked
	 * against the limits provided.
	 * 
	 * Runtime Exceptions may be thrown if the parser is unable to make sense of
	 * the value specification or if values lie outside the specified limits.
	 * 
	 * @param lowerLimit
	 *            the value lower limit of the Cron Value to create
	 * @param upperLimit
	 *            the value upper limit of the Cron Value to create
	 * @param dowSpecification
	 *            true if parsing a day of week specification.
	 * @param valueSpec
	 *            the specification of the value extracted from the cron
	 *            specification.
	 * 
	 * @return a reference to a newly constructed CronValue object initialized
	 *         as appropriate for the limits and value specification.
	 */
	private static CronValue parseMonthCronValue(int lowerLimit, int upperLimit,
			String valueSpec) {

		// The CronValue to be returned.
		CronValue cronValue = null;
		// The matcher to use.
		Matcher m = null;

		if (valueSpec == null) {
			throw new IllegalArgumentException(
					"Must provide a non-null value specification");
		}

		if (WILDCARD_PATTERN.equals(valueSpec)) {

			// This is a wildcard specification (*)
			cronValue = new WildcardCronValue(lowerLimit, upperLimit);

		} else if ((m = MONTH_SINGLE_VALUE_PATTERN.matcher(valueSpec)).matches()) {

			// This is a single value specification for a day of the week.
			int dowNumber = getMonth(m.group(1));
			cronValue = new SingleValueCronValue(lowerLimit, upperLimit,
					dowNumber);

		} else if ((m = MONTH_RANGE_PATTERN.matcher(valueSpec)).matches()) {

			// This is a range value specification for day of week
			int rangeLower = getMonth(m.group(1));
			int rangeUpper = getMonth(m.group(2));
			cronValue = new RangeCronValue(lowerLimit, upperLimit, rangeLower,
					rangeUpper);

		} else if ((m = STEP_PATTERN.matcher(valueSpec)).matches()) {

			// This is a step pattern (e.g. */3 - every third time period)
			try {
				int stepValue = Integer.parseInt(m.group(1));
				cronValue = new StepCronValue(lowerLimit, upperLimit, stepValue);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid step value: "
						+ valueSpec, e);
			}

		} else {

			// Unrecognized value pattern.
			throw new IllegalArgumentException("Invalid value specified: "
					+ valueSpec);

		}

		return cronValue;
	}
}
