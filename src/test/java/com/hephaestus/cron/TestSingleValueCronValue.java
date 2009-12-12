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
