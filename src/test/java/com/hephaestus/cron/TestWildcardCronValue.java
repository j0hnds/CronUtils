package com.hephaestus.cron;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This JUnit test evaluates the functionality of the Wildcard Cron Value.
 * 
 * @author Dave Sieh
 *
 */
public class TestWildcardCronValue {

	// The class under test.
	private WildcardCronValue cut;

	@Before
	public void setUp() throws Exception {
		// Set up the class under test.
		cut = new WildcardCronValue(0, 31);
	}

	@After
	public void tearDown() throws Exception {
		// Undefine the class under test.
		cut = null;
	}

	/**
	 * Verify that any value between 0 and 100 is an effective value.
	 */
	@Test
	public void testIsEffective() {
		for (int i = 0; i < 100; i++) {
			assertTrue("Value " + i + " should have been effective", cut
					.isEffective(i));
		}
	}

}
