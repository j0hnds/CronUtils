package com.hephaestus.cron;

import com.hephaestus.cron.CronValueFactory.CronValueCreator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Encapsulates the functionality of a cron specification. Parses/validates a
 * cron specification, then provides the tools to determine if dates are
 * effective relative to the specification.
 * 
 * @author Dave Sieh
 */
public class CronSpecification {

	// The indices of the time elements of the cron specification.
	private static final int MINUTE = 0;
	private static final int HOUR = 1;
	private static final int DAY = 2;
	private static final int MONTH = 3;
	private static final int DOW = 4;

	// Regular expression to identify when the cron expression is using a
	// short cut.
	private static final Pattern SHORTCUT_PATTERN = Pattern
			.compile("^@(yearly|annually|monthly|weekly|daily|midnight|hourly)$");

	// The map of cron specifications that map to the particular shortcut
	// names.
	private static final Map<String, String> SHORTCUT_MAP = new HashMap<String, String>();

	static {
		// Define the shortcut mappings.
		SHORTCUT_MAP.put("yearly", "0 0 1 1 *");
		SHORTCUT_MAP.put("annually", "0 0 1 1 *");
		SHORTCUT_MAP.put("monthly", "0 0 1 * *");
		SHORTCUT_MAP.put("weekly", "0 0 * * 0");
		SHORTCUT_MAP.put("daily", "0 0 * * *");
		SHORTCUT_MAP.put("midnight", "0 0 * * *");
		SHORTCUT_MAP.put("hourly", "0 * * * *");
	}

	// The original raw specification provided by the caller.
	private String rawSpecification;

	// The array of specification components.
	private String[] specification;

	// The list of minute specifications within the specification
	private List<CronValue> minutes;

	// The list of hour specifications within the specification
	private List<CronValue> hours;

	// The list of day specifications within the specification
	private List<CronValue> days;

	// The list of month specifications within the specification
	private List<CronValue> months;

	// The list of day of week specifications within the specification.
	private List<CronValue> daysOfWeek;

	/**
	 * Constructs a new CronSpecification object with the specified cron
	 * specification.
	 * 
	 * @param rawSpecification
	 *            the cron specification to interpret.
	 */
	public CronSpecification(String rawSpecification) {
		if (rawSpecification == null) {
			throw new IllegalArgumentException(
					"Cron Specification must be non-null");
		}

		this.rawSpecification = rawSpecification;

		// Deal with the shortcut possibility.
		Matcher m = SHORTCUT_PATTERN.matcher(rawSpecification);
		if (m.matches()) {
			// Looks like we received one of the shortcuts.
			String shortcut = m.group(1);
			if (!SHORTCUT_MAP.containsKey(shortcut)) {
				throw new IllegalStateException(
						"Mismatch between shortcut regular expression and short map: "
								+ shortcut);
			}

			rawSpecification = SHORTCUT_MAP.get(shortcut);
		}

		// Split the specification into its components
		specification = rawSpecification.split(" ");
		if (specification.length != 5) {
			throw new IllegalArgumentException(
					"Invalid Cron specification: less than 5 components: "
							+ rawSpecification);
		}

		minutes = new ArrayList<CronValue>();
		hours = new ArrayList<CronValue>();
		days = new ArrayList<CronValue>();
		months = new ArrayList<CronValue>();
		daysOfWeek = new ArrayList<CronValue>();

		parseSpecification(specification[MINUTE], minutes, CronValueFactory
				.getMinuteCreator());
		parseSpecification(specification[HOUR], hours, CronValueFactory
				.getHourCreator());
		parseSpecification(specification[DAY], days, CronValueFactory
				.getDayCreator());
		parseSpecification(specification[MONTH], months, CronValueFactory
				.getMonthCreator());
		parseSpecification(specification[DOW], daysOfWeek, CronValueFactory
				.getDOWCreator());
	}

	/**
	 * Parses the specification into the its component parts to make it more
	 * effecient to process when evaluating dates and to validate the
	 * specification.
	 * 
	 * @param specification
	 *            the raw specification to process.
	 * @param cronValues
	 *            reference to the empty list of cron values. This will be
	 *            populated by this method.
	 * @param cronValueCreator
	 *            the creator factory to use for this specification
	 */
	private void parseSpecification(String specification,
			List<CronValue> cronValues, CronValueCreator cronValueCreator) {
		if (specification == null) {
			throw new IllegalArgumentException(
					"Must specify a non-null specification");
		}

		String[] specComponents = specification.split(",");

		for (String specComponent : specComponents) {
			cronValues.add(cronValueCreator.createCronValue(specComponent));
		}
	}

	public boolean isDateEffective(Calendar dateToCheck) {
		boolean effective = false;

		boolean minutesOK = checkMinutes(dateToCheck.get(Calendar.MINUTE));
		boolean hoursOK = checkHours(dateToCheck.get(Calendar.HOUR_OF_DAY));
		boolean daysOK = checkDays(dateToCheck.get(Calendar.DATE));
		boolean monthsOK = checkMonths(dateToCheck.get(Calendar.MONTH) + 1);
		boolean dowOK = checkDaysOfWeek(dateToCheck.get(Calendar.DAY_OF_WEEK) - 1);

		effective = minutesOK && hoursOK && daysOK && monthsOK && dowOK;

		return effective;
	}
	
	public boolean isDateEffective(Date dateToCheck) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateToCheck);

		return isDateEffective(cal);
	}
	
	public boolean isDateEffective() {
		return isDateEffective(Calendar.getInstance());
	}

	private boolean checkMinutes(int minute) {
		return checkValues(minutes, minute);
	}

	private boolean checkHours(int hour) {
		return checkValues(hours, hour);
	}

	private boolean checkDays(int day) {
		return checkValues(days, day);
	}

	private boolean checkMonths(int month) {
		return checkValues(months, month);
	}

	private boolean checkDaysOfWeek(int dow) {
		return checkValues(daysOfWeek, dow);
	}

	private boolean checkValues(List<CronValue> valuePatterns, int value) {
		boolean effective = false;

		Iterator<CronValue> i = valuePatterns.iterator();
		while (i.hasNext() && (!effective)) {
			effective = i.next().isEffective(value);
		}

		return effective;
	}

	public String getRawSpecification() {
		return rawSpecification;
	}

}
