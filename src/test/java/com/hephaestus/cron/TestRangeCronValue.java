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
 * This JUnit test is designed to cover the functionality of a range Cron Value.
 * 
 * @author Dave Sieh
 */
public class TestRangeCronValue {

    // The test constants
    private static final int LOWER_LIMIT = 1;
    private static final int UPPER_LIMIT = 31;
    private static final int EFFECTIVE_VALUE = 10;
    private static final int INEFFECTIVE_LESS = 0;
    private static final int INEFFECTIVE_MORE = 32;

    // The class under test.
    private RangeCronValue cut;

    @Before
    public void setUp() throws Exception {
        // Set up the RangeCronValue to test.
        cut = new RangeCronValue(0, 31, LOWER_LIMIT, UPPER_LIMIT);
    }

    @After
    public void tearDown() throws Exception {
        // Undefine the class under test.
        cut = null;
    }

    /**
     * Positive test of effective value within range.
     */
    @Test
    public void testIsEffective() {
        assertTrue(cut.isEffective(EFFECTIVE_VALUE));
    }

    /**
     * Positive test of effective value at lower end of range.
     */
    @Test
    public void testIsEffectiveEqualLowerLimit() {
        assertTrue(cut.isEffective(LOWER_LIMIT));
    }

    /**
     * Positive test of effective value at upper end of range.
     */
    @Test
    public void testIsEffectiveEqualUpperLimit() {
        assertTrue(cut.isEffective(UPPER_LIMIT));
    }

    /**
     * Negative test of effective value less than lower end of range.
     */
    @Test
    public void testIsNotEffectiveLess() {
        assertFalse(cut.isEffective(INEFFECTIVE_LESS));
    }

    /**
     * Negative test of effective value greater than upper end of range.
     */
    @Test
    public void testIsNotEffectiveMore() {
        assertFalse(cut.isEffective(INEFFECTIVE_MORE));
    }

    /**
     * Positive test of all integer values between and including the lower and
     * upper values of the range.
     */
    @Test
    public void testIsEffectiveAll() {
        for (int i = LOWER_LIMIT; i <= UPPER_LIMIT; i++) {
            assertTrue("Value " + i + " should have been effective", cut
                    .isEffective(i));
        }
    }

}
