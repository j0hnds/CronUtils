package com.hephaestus.cron;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This JUnit test evaluates the functionality of the step cron value.
 * 
 * @author Dave Sieh
 */
public class TestStepCronValue {

	// Test constants
	private static final int STEP_VALUE = 3;

	// The class under test.
	private StepCronValue cut;

	@Before
	public void setUp() throws Exception {
		// Set up the class under test.
		cut = new StepCronValue(0, 31, STEP_VALUE);
	}

	@After
	public void tearDown() throws Exception {
		// Undefine the class under test.
		cut = null;
	}

	/**
	 * Positive test of effective value.
	 */
	@Test
	public void testIsEffective() {
		assertTrue(cut.isEffective(STEP_VALUE));
	}

	/**
	 * Positive test of a 10 effective values.
	 */
	@Test
	public void testIsEffectiveBunch() {
		for (int i = 0; i < (10 * STEP_VALUE); i += STEP_VALUE) {
			assertTrue("Value " + i + " should have been effective", cut
					.isEffective(STEP_VALUE));
		}
	}

	/**
	 * Negative test of somewhat less than 20 effective values.
	 */
	@Test
	public void testIsNotEffectiveBunch() {
		for (int i = 0; i < 20; i++) {
			if (i % STEP_VALUE != 0) {
				assertFalse("Value " + i + " should not have been effective",
						cut.isEffective(i));
			}
		}
	}

}
