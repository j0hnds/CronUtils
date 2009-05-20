package com.hephaestus.cron;

import com.hephaestus.cron.CronValueFactory.CronValueCreator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CronSpecification {
	
	private static final int MINUTE = 0;
	private static final int HOUR = 1;
	private static final int DAY = 2;
	private static final int MONTH = 3;
	private static final int DOW = 4;

	private String rawSpecification;
	private String[] specification;
	private List<CronValue> minutes;
	private List<CronValue> hours;
	private List<CronValue> days;
	private List<CronValue> months;
	private List<CronValue> daysOfWeek;
	
	public CronSpecification(String rawSpecification) {
		if (rawSpecification == null) {
			throw new IllegalArgumentException("Cron Specification must be non-null");
		}
		
		this.rawSpecification = rawSpecification;
		
		// Split the specification into its components
		specification = rawSpecification.split(" ");
		if (specification.length != 5) {
			throw new IllegalArgumentException("Invalid Cron specification: less than 5 components: " + rawSpecification);
		}
		
		minutes = new ArrayList<CronValue>();
		hours = new ArrayList<CronValue>();
		days = new ArrayList<CronValue>();
		months = new ArrayList<CronValue>();
		daysOfWeek = new ArrayList<CronValue>();
		
		parseSpecification(specification[MINUTE], minutes, CronValueFactory.getMinuteCreator());
		parseSpecification(specification[HOUR], hours, CronValueFactory.getHourCreator());
		parseSpecification(specification[DAY], days, CronValueFactory.getDayCreator());
		parseSpecification(specification[MONTH], months, CronValueFactory.getMonthCreator());
		parseSpecification(specification[DOW], daysOfWeek, CronValueFactory.getDOWCreator());
	}
	
	private void parseSpecification(String specification, List<CronValue> cronValues,
			CronValueCreator cronValueCreator) {
		if (specification == null) {
			throw new IllegalArgumentException("Must specify a non-null specification");
		}
		
		String[] specComponents = specification.split(",");
		
		for (String specComponent : specComponents) {
			cronValues.add(cronValueCreator.createCronValue(specComponent));
		}
	}

	public boolean isDateEffective(Date dateToCheck) {
		boolean effective = false;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateToCheck);
		
		boolean minutesOK = checkMinutes(cal.get(Calendar.MINUTE));
		boolean hoursOK = checkHours(cal.get(Calendar.HOUR_OF_DAY));
		boolean daysOK = checkDays(cal.get(Calendar.DATE));
		boolean monthsOK = checkMonths(cal.get(Calendar.MONTH) + 1);
		boolean dowOK = checkDaysOfWeek(cal.get(Calendar.DAY_OF_WEEK) - 1);
		
		effective = minutesOK && hoursOK && daysOK && monthsOK && dowOK;
		
		return effective;
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
		while (i.hasNext() && (! effective)) {
			effective = i.next().isEffective(value);
		}

		return effective;
	}

	public String getRawSpecification() {
		return rawSpecification;
	}

}
