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
 * Provides the implementation of a step Cron Value. This type of Cron Value
 * allows the definition of a step value against which other values are tested
 * for effectiveness. For example, if the step value specified was 3, then any
 * value that is evenly divisible by 3 is deemed effective.
 * 
 * This class is package-protected and should only be constructed using the
 * CronValueFactory.
 * 
 * @author Dave Sieh
 */
class StepCronValue extends CronValueBase {

	// The Step value to use to determine the effectiveness of other values.
	private int stepValue;

	/**
	 * Constructs a new StepCronValue with the specified limits and step value.
	 * 
	 * @param lowerLimit
	 *            the lower limit of values acceptable for this Cron Value.
	 * @param upperLimit
	 *            the upper limit of values acceptable for this Cron Value.
	 * @param stepValue
	 *            the step value to use for determining the effectiveness of
	 *            other values.
	 */
	public StepCronValue(int lowerLimit, int upperLimit, int stepValue) {
		super(lowerLimit, upperLimit);
		
		if (stepValue == 0 || stepValue > upperLimit) {
			throw new IllegalArgumentException("Invalid step value: "
					+ stepValue);
		}
		
		// Assign the step value.
		this.stepValue = stepValue;
	}

	public boolean isEffective(int value) {
		// Use the mod operator to determine effectiveness.
		return value % stepValue == 0;
	}

}
