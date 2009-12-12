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
 * This interface defines the method common to all Cron Values. The key aspect
 * of any cron value the the determination of whether a specified integer value
 * falls within the effective range of the Cron Value.
 * 
 * @author Dave Sieh
 */
public interface CronValue {

	/**
	 * Tests the specified value to determine if it is effective with respect to
	 * the cron value. The nature of what it means to be 'effective' depends on
	 * the implementation of the CronValue.
	 * 
	 * @param value
	 *            the value to test for effectiveness.
	 * 
	 * @return true if the value is deemed effective with respect to the
	 *         implementation of the CronValue.
	 */
	boolean isEffective(int value);
}
