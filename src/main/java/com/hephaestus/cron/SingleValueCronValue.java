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
 * Provides the implementation of a single Cron Value. This type of Cron Value
 * allows the definition of a single value against which tested values are
 * deemed to be effective.
 * 
 * This class is package-protected and should only be constructed using the
 * CronValueFactory.
 * 
 * @author Dave Sieh
 */
class SingleValueCronValue extends CronValueBase {

    // The single value against which other values are tested for effectiveness
    private int singleValue;

    /**
     * Constructs a new SingleValueCronValue object with the limits and value.
     * 
     * @param lowerLimit
     *            the lower limit of values acceptable for this Cron Value.
     * @param upperLimit
     *            the upper limit of values acceptable for this Cron Value.
     * @param singleValue
     *            the value against which other values are tested for
     *            effectiveness.
     */
    public SingleValueCronValue(int lowerLimit, int upperLimit, int singleValue) {
        super(lowerLimit, upperLimit);

        if (!isValueWithinLimits(singleValue)) {
            throw new IllegalArgumentException("Invalid value specified: "
                    + singleValue);
        }

        this.singleValue = singleValue;
    }

    public boolean isEffective(int value) {
        // Value must equal test value.
        return singleValue == value;
    }

}
