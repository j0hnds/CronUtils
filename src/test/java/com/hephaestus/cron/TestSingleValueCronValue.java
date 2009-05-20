package com.hephaestus.cron;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSingleValueCronValue {
	
	private static int GOOD_TEST_VALUE = 11;
	private static int BAD_TEST_VALUE = 8;
	
	private SingleValueCronValue cut;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		cut = new SingleValueCronValue(0, 31, GOOD_TEST_VALUE); 
	}

	@After
	public void tearDown() throws Exception {
		cut = null;
	}

	@Test
	public void testIsEffective() {
		assertTrue(cut.isEffective(GOOD_TEST_VALUE));
	}

	@Test
	public void testIsNotEffective() {
		assertFalse(cut.isEffective(BAD_TEST_VALUE));
	}

}
