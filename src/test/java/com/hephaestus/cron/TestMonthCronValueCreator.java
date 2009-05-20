package com.hephaestus.cron;

import static org.junit.Assert.*;
import com.hephaestus.cron.CronValueFactory.CronValueCreator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestMonthCronValueCreator {

	private static final String WILDCARD_VALUE = "*";
	private static final String SINGLE_VALUE = "12";
	private static final int LOWER_LIMIT = 3;
	private static final int UPPER_LIMIT = 11;
	private static final String RANGE_VALUE = LOWER_LIMIT + "-" + UPPER_LIMIT;
	private static final int STEP = 2;
	private static final String STEP_VALUE = "*/" + STEP;
	private static final String INVALID_WILDCARD = "%";
	private static final String INVALID_SINGLE_VALUE = "62";
	private static final String TWISTED_RANGE = "11-10";
	private static final String INVALID_LOWER_RANGE = "33-11";
	private static final String INVALID_UPPER_RANGE = "8-42";

	private CronValueCreator cut;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		cut = CronValueFactory.getMonthCreator();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetMinuteWildcardCreator() {
		CronValue cv = cut.createCronValue(WILDCARD_VALUE);
		assertNotNull(cv);
		// Any value should work
		for (int i = 0; i < 59; i++) {
			assertTrue("Value " + i + " should have been effective", cv
					.isEffective(i));
		}
	}

	@Test
	public void testGetMinuteSingleValueCreator() {
		CronValue cv = cut.createCronValue(SINGLE_VALUE);
		int sv = Integer.parseInt(SINGLE_VALUE);
		assertNotNull(cv);
		assertTrue(cv.isEffective(sv));
		assertFalse(cv.isEffective(sv - 1));
		assertFalse(cv.isEffective(sv + 1));
	}

	@Test
	public void testGetMinuteRangeCreator() {
		CronValue cv = cut.createCronValue(RANGE_VALUE);
		assertNotNull(cv);
		// Any value in range should work
		for (int i = LOWER_LIMIT; i <= UPPER_LIMIT; i++) {
			assertTrue("Value " + i + " should have been effective", cv
					.isEffective(i));
		}
		assertFalse(cv.isEffective(LOWER_LIMIT - 1));
		assertFalse(cv.isEffective(UPPER_LIMIT + 1));
	}

	@Test
	public void testGetMinuteStepCreator() {
		CronValue cv = cut.createCronValue(STEP_VALUE);
		assertNotNull(cv);
		// Any value of step should work
		for (int i = 0; i < (10 * STEP); i += STEP) {
			assertTrue("Value " + i + " should have been effective", cv
					.isEffective(i));
		}
		// Any value out of step should fail
		for (int i = 0; i < 100; i++) {
			if (i % STEP != 0) {
				assertFalse("Value " + i + " should have been ineffective", cv
						.isEffective(i));
			}
		}
	}
	
	@Test
	public void testInvalidWildcard() {
		
		try {
			cut.createCronValue(INVALID_WILDCARD);
			fail("Should have thrown an exception");
		} catch (Exception e) {
		}
	}

	@Test
	public void testInvalidSingleValue() {
		try {
			cut.createCronValue(INVALID_SINGLE_VALUE);
			fail("Should have thrown an exception");
		} catch (Exception e) {
		}
	}

	@Test
	public void testInvalidLowerRange() {
		try {
			cut.createCronValue(INVALID_LOWER_RANGE);
			fail("Should have thrown an exception");
		} catch (Exception e) {
		}
	}

	@Test
	public void testInvalidUpperRange() {
		try {
			cut.createCronValue(INVALID_UPPER_RANGE);
			fail("Should have thrown an exception");
		} catch (Exception e) {
		}
	}

	@Test
	public void testTwistedRange() {
		try {
			cut.createCronValue(TWISTED_RANGE);
			fail("Should have thrown an exception");
		} catch (Exception e) {
		}
	}

}
