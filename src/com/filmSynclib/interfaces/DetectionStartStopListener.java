package com.filmSynclib.interfaces;

/**
 * Interface implement where we start record and recognize audio.
 * @author fingent
 *
 */
public interface DetectionStartStopListener {
	
	/**
	 * Function to start listening the audio stream.
	 */
	public void onStartListerning();
	
	/**
	 * Function to stop listening the audio stream.
	 */
	public void onStopListerning();
	
}
