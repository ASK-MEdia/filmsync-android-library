package com.filmSynclib.model;

import com.filmSynclib.math.FFT;

/**
 * Class to store the recorded audio data
 * @author fingent
 *
 */
public class DataBlock 
{
	private double[] block;
	/**
	 * Constructor 
	 * @param buffer buffer to save data 
	 * @param blockSize // size of the block array
	 * @param bufferReadSize recored buffer size
	 */
	public DataBlock(short[] buffer, int blockSize, int bufferReadSize)
	{
		block = new double[blockSize];

		for (int i = 0; i < blockSize && i < bufferReadSize; i++) {
			block[i] = (double) buffer[i];
		}
		
	}

	public DataBlock()
	{

	}
	/**
	 * Function to set the block variable
	 * @param block block array
	 */

	public void setBlock(double[] block) 
	{
		this.block = block;
	}
	/**
	 * Function returns the block array
	 * @return block array
	 */

	public double[] getBlock() 
	{
		return block;
	}
	/**
	 * Function
	 * @return spectrum object
	 */

	public Spectrum FFT()
	{
		return new Spectrum(FFT.magnitudeSpectrum(block));
	}
}
