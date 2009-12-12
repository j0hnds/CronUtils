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
 * Provides the implementation of a wildcard Cron Value. This type of Cron Value
 * provides a scenario where all tested values are deemed effective. This is
 * used when the value of a particular cron component is not material to the
 * determination of effectiveness.
 * 
 * This class is package-protected and should only be constructed using the
 * CronValueFactory.
 * 
 * @author Dave Sieh
 */
class WildcardCronValue extends CronValueBase {

	/**
	 * Constructs a new WildcardCronValue with the specified limits.
	 * 
	 * @param lowerLimit
	 *            the lower limit of values acceptable for this Cron Value.
	 * @param upperLimit
	 *            the upper limit of values acceptable for this Cron Value.
	 */
	public WildcardCronValue(int lowerLimit, int upperLimit) {
		super(lowerLimit, upperLimit);
	}

	public boolean isEffective(int value) {
		// Any value is effective.
		return true;
	}

}
