package com.filmSynclib.interfaces;

/**
 *Implement on where we need to get detected code 
 * @author fingent
 *
 */
public interface CodeDetectedListener {
	
	/**
	 * Function to execute when code detected from recorded audio stream.
	 * @param code
	 */
	public void onCodeDetected(String code);
	
	/**
	 * Function to execute when code header detected but not detected the full code.
	 */
	
	public void onCodeMissed();
	
	/**
	 * Function to execute when code header detected.
	 * @param msg message.
	 */
	public void onCodeHeaderDetected(String msg);
}
