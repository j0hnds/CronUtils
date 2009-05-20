package com.hephaestus.cron;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestRangeCronValue {

	private static final int LOWER_LIMIT = 1;
	private static final int UPPER_LIMIT = 31;
	private static final int EFFECTIVE_VALUE = 10;
	private static final int INEFFECTIVE_LESS = 0;
	private static final int INEFFECTIVE_MORE = 32;

	private RangeCronValue cut;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		cut = new RangeCronValue(0, 31, LOWER_LIMIT, UPPER_LIMIT);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsEffective() {
		assertTrue(cut.isEffective(EFFECTIVE_VALUE));
	}

	@Test
	public void testIsEffectiveEqualLowerLimit() {
		assertTrue(cut.isEffective(LOWER_LIMIT));
	}

	@Test
	public void testIsEffectiveEqualUpperLimit() {
		assertTrue(cut.isEffective(UPPER_LIMIT));
	}

	@Test
	public void testIsNotEffectiveLess() {
		assertFalse(cut.isEffective(INEFFECTIVE_LESS));
	}

	@Test
	public void testIsNotEffectiveMore() {
		assertFalse(cut.isEffective(INEFFECTIVE_MORE));
	}

	@Test
	public void testIsEffectiveAll() {
		for (int i = LOWER_LIMIT; i <= UPPER_LIMIT; i++) {
			assertTrue("Value " + i + " should have been effective", cut
					.isEffective(i));
		}
	}

}
