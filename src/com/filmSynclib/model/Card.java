package com.filmSynclib.model;

/**
 * Class to save card details
 * @author fingent
 *
 */
public class Card {

	private String cardProjectID; 		//project identifier of card
	private String cardID;				//Card identifier
	private String cardTitle;			//Card title
	private String cardTwitterSearch;	//card twitter search
	private String cardContent;			//card content url
	private String warningMSG;
	public String getWarningMSG() {
		return warningMSG;
	}

	public void setWarningMSG(String warningMSG) {
		this.warningMSG = warningMSG;
	}

	/**
	 * Function to return the project identifier of card
	 * @return project identifier of card
	 */
	public String getCardProjectID() {
		return cardProjectID;
	}
	
	/**
	 * Function to set the project identifier of card
	 * @param cardProjectID project identifier of card
	 */
	public void setCardProjectID(String cardProjectID) {
		this.cardProjectID = cardProjectID;
	}
	
	/**
	 * Function to get the card identifier
	 * @return card identifier
	 */
	public String getCardID() {
		return cardID;
	}
	
	/**
	 * Function to set the card identifier
	 * @param cardID card identifier
	 */
	public void setCardID(String cardID) {
		this.cardID = cardID;
	}
	
	/**
	 * Function to get the card title
	 * @return card title
	 */
	public String getCardTitle() {
		return cardTitle;
	}
	
	/**
	 * Function to set the card title
	 * @param cardTitle card title
	 */
	public void setCardTitle(String cardTitle) {
		this.cardTitle = cardTitle;
	}
	
	/**
	 * Function to get the card twitter search
	 * @return card twitter search
	 */
	public String getCardTwitterSearch() {
		return cardTwitterSearch;
	}
	
	/**
	 * Function to set the card twitter search
	 * @param cardTwitterSearch card twitter search
	 */
	public void setCardTwitterSearch(String cardTwitterSearch) {
		this.cardTwitterSearch = cardTwitterSearch;
	}
	
	/**
	 * Function to get the card content url
	 * @return card content url
	 */
	public String getCardContent() {
		return cardContent;
	}
	
	/**
	 * Function to set the card content url
	 * @param cardContent card content url
	 */
	public void setCardContent(String cardContent) {
		this.cardContent = cardContent;
	}
	
}
