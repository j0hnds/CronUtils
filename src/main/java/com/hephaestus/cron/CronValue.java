package com.hephaestus.cron;

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
