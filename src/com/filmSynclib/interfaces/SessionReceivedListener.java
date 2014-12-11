package com.filmSynclib.interfaces;

import com.filmSynclib.model.Card;
import com.filmSynclib.model.Session;

/**
 * Interface to handle Session 
 * @author fingent
 *
 */
public interface SessionReceivedListener {
	
	/**
	 * Function to execute when session received.
	 * @param session received session data
	 */
	public void onSessionReceived(Session session);
	
	
	/**
	 *Function to execute when session re-authenticate during fetching the details for codeNo. 
	 * @param session received session data
	 * @param codeNo code number
	 */
	public void onSessionReceivedForCard(Session session,String codeNo);
	
		
	/**
	 * Function to execute when session re-authenticate during fetching the details for card. 
	 * @param session received session data.
	 * @param card card details.
	 */
	public void onSessionReceivedForProject(Session session,Card card);

	
	/**
	 * Function to execute when session expired during fetching the details for codeNo.
	 * @param codeNo
	 */
	public void onSessionExpiredWithCard(String codeNo);
	
	
	/**
	 * Function to execute when session expired during fetching the details for card.
	 * @param card card details.
	 */
	public void onSessionExpiredWithProject(Card card);
	
}
