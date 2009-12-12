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

/**
 * Abstract base implementation of a CronValue. The point of this class is to
 * define a set of limits between which a cron value must exist. For example, if
 * the Cron Value represents a minute, the lower limit would be 0 and the upper
 * limit would be 59. But if the Cron Value represented an hour, the lower and
 * upper limits would be 0 and 23 respectively.
 * 
 * @author Dave Sieh
 */
public abstract class CronValueBase implements CronValue {

    // The lower limit of the value
    private int lowerLimit;

    // The upper limit of the value
    private int upperLimit;

    /**
     * Constructs a new CronValueBase object with limits.
     * 
     * @param lowerLimit
     *            the lower limit of the value
     * @param upperLimit
     *            the upper limit of the value.
     */
    public CronValueBase(int lowerLimit, int upperLimit) {
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    /**
     * Tests a specified value to determine if the value falls within the lower
     * and upper limits inclusively.
     * 
     * @param value
     *            the value to be tested for inclusion.
     * 
     * @return true if the value false within the range limits of the CronValue.
     */
    public final boolean isValueWithinLimits(int value) {
        return value >= lowerLimit && value <= upperLimit;
    }
}
