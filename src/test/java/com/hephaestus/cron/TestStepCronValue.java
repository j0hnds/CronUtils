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
