package com.filmSynclib.model;

/**
 * Class to store tone details.
 * @author fingent
 *
 */
public class Tone {

	private int highFrequency; //tone high frequency

	private char key; // key correspond to the frequency

	private static int FREQUENCY_DELTA = 2; //

	/**
	 * Constructor
	 * @param highFrequency tone high frequency
	 * @param key key correspond to the frequency
	 */
	public Tone(int highFrequency, char key) 
	{
		this.highFrequency = highFrequency;
		this.key = key;
	}
	/**
	 * Function to get the high frequency of the tone
	 * @return high frequency of the tone
	 */
	public int getHighFrequency()
	{
		return highFrequency;
	}

	/**
	 * Function to get key character 
	 * @return key character
	 */
	public char getKey()
	{
		return key;
	}

	/**
	 * Check frequency match with  tone's high frequency 
	 * @param highFrequency frequency to check with max frequency
	 * @return true if frequencies matches 
	 */
	public boolean match(int highFrequency) 
	{
		if((highFrequency - this.highFrequency) * (highFrequency - this.highFrequency) < FREQUENCY_DELTA * FREQUENCY_DELTA)
			return true;

		return false;
	}
}
