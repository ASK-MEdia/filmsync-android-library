package com.filmSynclib.model;

/**
 * Class to store session data
 * @author fingent
 *
 */
public class Session {

	private String sessionID; //Session code
	private String sessionStatus;//session status
	private String warningMSG; //warning message
	
	/**
	 * Constructor
	 */
	public Session(){

	}
	
	/**
	 * Constructor
	 * @param sessionID session data.
	 * @param sessionStatus session status.
	 */
	public Session(String sessionID, String sessionStatus){

		this.sessionID=sessionID;
		this.sessionStatus=sessionStatus;
	}


	/**
	 * Function to get the warning message.
	 * @return warning message
	 */
	public String getWarningMSG() {
		return warningMSG;
	}

	/**
	 * Function to set the warning message
	 * @param warningMSG message
	 */
	public void setWarningMSG(String warningMSG) {
		this.warningMSG = warningMSG;
	}


	/**
	 * Function to get the session code
	 * @return session code
	 */
	public String getSessionID() {
		return sessionID;
	}
	
	/**
	 * Function to set the session code
	 * @param sessionID session code
	 */
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	
	/**
	 * Function to get the session status
	 * @return session status
	 */
	public String getSessionStatus() {
		return sessionStatus;
	}
	
	/**
	 * Function to set the session status.
	 * @param sessionStatus session status.
	 */
	public void setSessionStatus(String sessionStatus) {
		this.sessionStatus = sessionStatus;
	}
}
