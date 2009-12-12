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
