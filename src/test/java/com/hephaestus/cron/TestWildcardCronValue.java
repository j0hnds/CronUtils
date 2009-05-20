package com.hephaestus.cron;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestWildcardCronValue {

	private WildcardCronValue cut;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		cut = new WildcardCronValue(0, 31);
	}

	@After
	public void tearDown() throws Exception {
		cut = null;
	}

	@Test
	public void testIsEffective() {
		for (int i = 0; i < 100; i++) {
			assertTrue("Value " + i + " should have been effective", cut
					.isEffective(i));
		}
	}

}
