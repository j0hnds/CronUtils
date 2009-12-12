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
 * Provides the implementation of a range Cron Value. This type of Cron Value
 * allows the definition of a lower and upper limit between which tested values
 * are deemed to be effective. The inclusion test is inclusive to the end points
 * of the range definition.
 * 
 * This class is package-protected and should only be constructed using the
 * CronValueFactory.
 * 
 * @author Dave Sieh
 */
class RangeCronValue extends CronValueBase {

    // The lower limit of the range
    private int rangeLower;

    // The upper limit of the range
    private int rangeUpper;

    /**
     * Constructs a new RangeCronValue object with the specified limits.
     * 
     * Runtime Exceptions may be thrown if the range values are inappropriate or
     * if the range value is reversed.
     * 
     * @param lowerLimit
     *            the lower limit of values acceptable for this Cron Value.
     * @param upperLimit
     *            the upper limit of values acceptable for this Cron Value.
     * @param rangeLower
     *            the lower limit of the range of effective values.
     * @param rangeUpper
     *            the upper limit of the range of effective values.
     */
    public RangeCronValue(int lowerLimit, int upperLimit, int rangeLower,
            int rangeUpper) {
        super(lowerLimit, upperLimit);

        if (!isValueWithinLimits(rangeLower)) {
            throw new IllegalArgumentException("Invalid lower range value: "
                    + rangeLower);
        }
        if (!isValueWithinLimits(rangeUpper)) {
            throw new IllegalArgumentException("Invalid lower range value: "
                    + rangeUpper);
        }
        if (rangeLower > rangeUpper) {
            throw new IllegalArgumentException(
                    "Lower limit must be less than or equal to the upper limit");
        }

        this.rangeLower = rangeLower;
        this.rangeUpper = rangeUpper;
    }

    public boolean isEffective(int value) {
        // An inclusive test of the value.
        return rangeLower <= value && value <= rangeUpper;
    }

}
