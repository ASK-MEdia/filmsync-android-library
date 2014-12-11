package com.filmSynclib.interfaces;

import com.filmSynclib.model.Card;

/**
 * interface implement where we need to listen card detection 
 * @author fingent
 *
 */
public interface CardDetectionListener {
	
	/**
	 * Function to execute when card detected. 
	 * @param card card model object.
	 */
	public void onCardDetected(Card card);
	
	/**
	 * Function to execute when deleted card detected.
	 * @param cardNo card identifier.
	 */
	
	public void onCardDeleted(String cardNo);
}
