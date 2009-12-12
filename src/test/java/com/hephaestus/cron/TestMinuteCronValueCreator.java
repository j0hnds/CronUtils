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
import com.hephaestus.cron.CronValueFactory.CronValueCreator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This unit test case is focused on testing the functionality of the cron value
 * creator for minute information.
 */
public class TestMinuteCronValueCreator {

    // Test constants
    private static final String WILDCARD_VALUE = "*";
    private static final String SINGLE_VALUE = "12";
    private static final int LOWER_LIMIT = 45;
    private static final int UPPER_LIMIT = 59;
    private static final String RANGE_VALUE = LOWER_LIMIT + "-" + UPPER_LIMIT;
    private static final int STEP = 30;
    private static final String STEP_VALUE = "*/" + STEP;
    private static final String INVALID_WILDCARD = "%";
    private static final String INVALID_SINGLE_VALUE = "62";
    private static final String TWISTED_RANGE = "30-28";
    private static final String INVALID_LOWER_RANGE = "60-61";
    private static final String INVALID_UPPER_RANGE = "23-64";

    // The class under test.
    private CronValueCreator cut;

    @Before
    public void setUp() throws Exception {
        // Set up the class under test
        cut = CronValueFactory.getMinuteCreator();
    }

    @After
    public void tearDown() throws Exception {
        // undefine the class under test
        cut = null;
    }

    /**
     * Tests the creation of the wildcard cron value.
     */
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

    /**
     * Tests the creation of a single value cron value.
     */
    @Test
    public void testGetMinuteSingleValueCreator() {
        CronValue cv = cut.createCronValue(SINGLE_VALUE);
        int sv = Integer.parseInt(SINGLE_VALUE);
        assertNotNull(cv);
        assertTrue(cv.isEffective(sv));
        assertFalse(cv.isEffective(sv - 1));
        assertFalse(cv.isEffective(sv + 1));
    }

    /**
     * Tests the creation of a range cron value.
     */
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

    /**
     * Tests the creation of a step cron value.
     */
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

    /**
     * Tests the creation with an invalid wildcard (invalid value in general).
     */
    @Test
    public void testInvalidWildcard() {

        try {
            cut.createCronValue(INVALID_WILDCARD);
            fail("Should have thrown an exception");
        }
        catch (Exception e) {
        }
    }

    /**
     * Tests the creation of the cron value with an invalid single value.
     */
    @Test
    public void testInvalidSingleValue() {
        try {
            cut.createCronValue(INVALID_SINGLE_VALUE);
            fail("Should have thrown an exception");
        }
        catch (Exception e) {
        }
    }

    /**
     * Tests the creation of the range cron value with an invalid lower range.
     */
    @Test
    public void testInvalidLowerRange() {
        try {
            cut.createCronValue(INVALID_LOWER_RANGE);
            fail("Should have thrown an exception");
        }
        catch (Exception e) {
        }
    }

    /**
     * Tests the creation of the range cron value with an invalue upper range.
     */
    @Test
    public void testInvalidUpperRange() {
        try {
            cut.createCronValue(INVALID_UPPER_RANGE);
            fail("Should have thrown an exception");
        }
        catch (Exception e) {
        }
    }

    /**
     * Tests the creation of the range cron value with a twisted range (lower >
     * upper).
     */
    @Test
    public void testTwistedRange() {
        try {
            cut.createCronValue(TWISTED_RANGE);
            fail("Should have thrown an exception");
        }
        catch (Exception e) {
        }
    }

}
