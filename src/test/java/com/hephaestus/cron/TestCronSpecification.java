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

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

/**
 * This Unit Test Case is used to validate the functionality of the
 * CronSpecification class.
 * 
 * @author Dave Sieh
 */
public class TestCronSpecification {

	private static final SimpleDateFormat SDF = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");
	private static final String JANUARY_1_2009 = "2009/01/01 01:01:00";
	private static final String JANUARY_1_2009_MIDNIGHT = "2009/01/01 00:00:00";
	private static final String MAY_24_2009_MIDNIGHT = "2009/05/24 00:00:00";
	private static final String MINUTE_ONE = "1 * * * *";
	private static final String HOUR_ONE = "* 1 * * *";
	private static final String DAY_ONE = "* * 1 * *";
	private static final String MONTH_ONE = "* * * 1 *";
	private static final String DOW_FOUR = "* * * * 4";
	private static final String FULL_SPEC = "1 1 1 1 4";
	private static final String FULL_WRONG_SPEC = "2 2 2 2 3";
	private static final String VALID_ALL_RANGES = "0-2 0-2 1-3 1-2 2-5";
	private static final String MARCH_3_2009 = "2009/03/03 03:03:00";
	private static final String VALID_ALL_STEPS = "*/3 */3 */3 */3 */2";
	private static final String INVALID_ALL_STEPS = "*/3 */3 */3 */3 */3";
	private static final String VALID_COMBO = "45-59,3 8,*/3 */2,1-4 * 6,*/2";

	/**
	 * Test to verify only the minute portion of the cron specification.
	 * 
	 * @throws ParseException
	 *             If there was an error parsing the test date.
	 */
	@Test
	public void testIsMinuteOneDateEffective() throws ParseException {
		CronSpecification cs = new CronSpecification(MINUTE_ONE);
		assertTrue(cs.isDateEffective(SDF.parse(JANUARY_1_2009)));
	}

	/**
	 * Test to verify only the hour portion of the cron specification.
	 * 
	 * @throws ParseException
	 *             If there was an error parsing the test date.
	 */
	@Test
	public void testIsHourOneDateEffective() throws ParseException {
		CronSpecification cs = new CronSpecification(HOUR_ONE);
		assertTrue(cs.isDateEffective(SDF.parse(JANUARY_1_2009)));
	}

	/**
	 * Test to verify only the day portion of the cron specification
	 * 
	 * @throws ParseException
	 *             If there was an error parsing the test date.
	 */
	@Test
	public void testIsDayOneDateEffective() throws ParseException {
		CronSpecification cs = new CronSpecification(DAY_ONE);
		assertTrue(cs.isDateEffective(SDF.parse(JANUARY_1_2009)));
	}

	/**
	 * Test to verify only the month portion of the cron specification.
	 * 
	 * @throws ParseException
	 *             if there was an error parsing the test date.
	 */
	@Test
	public void testIsMonthOneDateEffective() throws ParseException {
		CronSpecification cs = new CronSpecification(MONTH_ONE);
		assertTrue(cs.isDateEffective(SDF.parse(JANUARY_1_2009)));
	}

	/**
	 * Test to verify on the day of week portion of the cron specification.
	 * 
	 * @throws ParseException
	 *             if there was an error parsing the test date.
	 */
	@Test
	public void testIsDOWFourDateEffective() throws ParseException {
		CronSpecification cs = new CronSpecification(DOW_FOUR);
		assertTrue(cs.isDateEffective(SDF.parse(JANUARY_1_2009)));
	}

	/**
	 * Test to verify a cron specification that has every component populated.
	 * 
	 * @throws ParseException
	 *             if there was an error parsing the test date.
	 */
	@Test
	public void testIsFullSpecEffective() throws ParseException {
		CronSpecification cs = new CronSpecification(FULL_SPEC);
		assertTrue(cs.isDateEffective(SDF.parse(JANUARY_1_2009)));
	}

	/**
	 * Test to verify an ineffective cron specification that has every component
	 * populated.
	 * 
	 * @throws ParseException
	 *             if there was an error parsing the test date.
	 */
	@Test
	public void testIsFullWrongSpecNotEffective() throws ParseException {
		CronSpecification cs = new CronSpecification(FULL_WRONG_SPEC);
		assertFalse(cs.isDateEffective(SDF.parse(JANUARY_1_2009)));
	}

	/**
	 * Test to verify an effective cron specification where each component of
	 * the specification is a range.
	 * 
	 * @throws ParseException
	 *             if there was an error parsing the test date.
	 */
	@Test
	public void testIsValidAllRangesEffective() throws ParseException {
		CronSpecification cs = new CronSpecification(VALID_ALL_RANGES);
		assertTrue(cs.isDateEffective(SDF.parse(JANUARY_1_2009)));
	}

	/**
	 * Test to verify an effective cron specification where each component of
	 * the specification is a step.
	 * 
	 * @throws ParseException
	 *             if there was an error parsing the test date.
	 */
	@Test
	public void testIsValidAllStepsEffective() throws ParseException {
		CronSpecification cs = new CronSpecification(VALID_ALL_STEPS);
		assertTrue(cs.isDateEffective(SDF.parse(MARCH_3_2009)));
	}

	/**
	 * Test to verify an ineffective cron specification where each component of
	 * the specification is a step.
	 * 
	 * @throws ParseException
	 *             if there was an error parsing the test date.
	 */
	@Test
	public void testIsInValidAllStepsEffective() throws ParseException {
		CronSpecification cs = new CronSpecification(INVALID_ALL_STEPS);
		assertFalse(cs.isDateEffective(SDF.parse(MARCH_3_2009)));
	}

	/**
	 * Test to verify an effective cron specification where each component of
	 * the specification uses a different style of cron value (single value,
	 * range, step, wildcard) and has a list of types.
	 * 
	 * @throws ParseException
	 *             if there was an error parsing the test date.
	 */
	@Test
	public void testIsValidComboEffective() throws ParseException {
		CronSpecification cs = new CronSpecification(VALID_COMBO);
		assertTrue(cs.isDateEffective(SDF.parse(MARCH_3_2009)));
	}

	/**
	 * Test to verify that the getRawSpecification getter works.
	 */
	@Test
	public void testGetRawSpecification() {
		CronSpecification cs = new CronSpecification(MINUTE_ONE);
		assertEquals(MINUTE_ONE, cs.getRawSpecification());
	}
	
	@Test
	public void testYearlyShortcut() throws ParseException {
		CronSpecification cs = new CronSpecification("@yearly");
		assertTrue(cs.isDateEffective(SDF.parse(JANUARY_1_2009_MIDNIGHT)));
	}

	@Test
	public void testAnnuallyShortcut() throws ParseException {
		CronSpecification cs = new CronSpecification("@annually");
		assertTrue(cs.isDateEffective(SDF.parse(JANUARY_1_2009_MIDNIGHT)));
	}

	@Test
	public void testMonthlyShortcut() throws ParseException {
		CronSpecification cs = new CronSpecification("@monthly");
		assertTrue(cs.isDateEffective(SDF.parse(JANUARY_1_2009_MIDNIGHT)));
	}

	@Test
	public void testWeeklyShortcut() throws ParseException {
		CronSpecification cs = new CronSpecification("@weekly");
		assertTrue(cs.isDateEffective(SDF.parse(MAY_24_2009_MIDNIGHT)));
	}

	@Test
	public void testDailyShortcut() throws ParseException {
		CronSpecification cs = new CronSpecification("@daily");
		assertTrue(cs.isDateEffective(SDF.parse(MAY_24_2009_MIDNIGHT)));
	}

	@Test
	public void testHoulyShortcut() throws ParseException {
		CronSpecification cs = new CronSpecification("@hourly");
		assertTrue(cs.isDateEffective(SDF.parse(MAY_24_2009_MIDNIGHT)));
	}

}
