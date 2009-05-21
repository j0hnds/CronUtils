package com.hephaestus.cron;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This JUnit test evaluations the functionality of the single value Cron Value.
 * 
 * @author Dave Sieh
 */
public class TestSingleValueCronValue {
	
	// Test constants
	private static int GOOD_TEST_VALUE = 11;
	private static int BAD_TEST_VALUE = 8;
	
	// The class under test.
	private SingleValueCronValue cut;

	@Before
	public void setUp() throws Exception {
		// Set up the class under test
		cut = new SingleValueCronValue(0, 31, GOOD_TEST_VALUE); 
	}

	@After
	public void tearDown() throws Exception {
		// Tear down the class under test.
		cut = null;
	}

	/**
	 * Positive test of effective value.
	 */
	@Test
	public void testIsEffective() {
		assertTrue(cut.isEffective(GOOD_TEST_VALUE));
	}

	/**
	 * Negative test of effective value.
	 */
	@Test
	public void testIsNotEffective() {
		assertFalse(cut.isEffective(BAD_TEST_VALUE));
	}

}
