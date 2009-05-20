package com.hephaestus.cron;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestStepCronValue {

	private static final int STEP_VALUE = 3;

	private StepCronValue cut;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		cut = new StepCronValue(STEP_VALUE);
	}

	@After
	public void tearDown() throws Exception {
		cut = null;
	}

	@Test
	public void testIsEffective() {
		assertTrue(cut.isEffective(STEP_VALUE));
	}

	@Test
	public void testIsEffectiveBunch() {
		for (int i = 0; i < (10 * STEP_VALUE); i += STEP_VALUE) {
			assertTrue("Value " + i + " should have been effective", cut
					.isEffective(STEP_VALUE));
		}
	}

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
