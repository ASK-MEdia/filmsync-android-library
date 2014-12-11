package com.filmSynclib.model;


/**
 * Stores the spectrum details.
 * @author fingent
 *
 */
public class Spectrum {

	private double[] spectrum; //spectrum array
	private int length; // length of spectrum array
	private int start;// start index of array
	private int end; // end index of array
	/**
	 * Constructor
	 * @param spectrum spectrum array
	 */
	public Spectrum(double[] spectrum) 
	{
		this.spectrum = spectrum;
		this.length = spectrum.length;
	}

	/**
	 * Constructor
	 * @param spectrum spectrum object
	 * @param start start index of array
	 * @param end end index of array
	 */
	public Spectrum(Spectrum spectrum,int start,int end){
		this.spectrum = spectrum.spectrum;
		this.length = spectrum.length;
		this.start=start;
		this.end=end;
	}
	
	/**
	 * Function to normalize the spectrum array, finds the maximum value and divide each value with max value.
	 */
	public void normalize()
	{
		double maxValue = 0.0;

		for(int i=0;i<length; ++i){

			if(maxValue < spectrum[i]){

				maxValue = spectrum[i];
			}
		}

		if(maxValue != 0)			
			for(int i=0;i<length; ++i)
				spectrum[i] /= maxValue;
	}
	/**
	 * Function to get spectrum array value
	 * @param index index of the value
	 * @return value at index
	 */

	public double get(int index)
	{/**
		 * Function to invoke when code detected.
		 * @param code Detected code.
		 */
		return spectrum[index];
	}
	/**
	 * Function returns the length of the spectrum array
	 * @return length of the spectrum array
	 */
	public int length() 
	{
		return length;
	}
  /**
   * Function returns the max value of the array
   * @return max value of the array
   */
	public int getMax()
	{
		int max = 0;
		double maxValue = 0;

		for(int i = start; i <= end; ++i)
			if(maxValue < spectrum[i])
			{
				maxValue = spectrum[i];
				max = i;
			}
		return max;
	}
}
