package com.filmSynclib.model;

import java.util.ArrayList;

/**
 * Class to save project details
 * @author fingent
 *
 */

public class Project {
	
	private String projectID;		//Project identifier.
	private String projectTitle;	//Project Title.
	private String twitterSearch;	//Project twitter tag.
	private String decription;		//Project description.
	private ArrayList<Card> cards;	//All cards under the project
	private String warningMSG;
	
	public String getWarningMSG() {
		return warningMSG;
	}

	public void setWarningMSG(String warningMSG) {
		this.warningMSG = warningMSG;
	}

	/**
	 * Function to get Project identifier.
	 * @return Project identifier
	 */
	public String getProjectID() {
		return projectID;
	}
	
	/**
	 * Function to set project identifier.
	 * @param projectID project identifier
	 */
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	
	/**
	 * Function to get project title
	 * @return project title
	 */
	public String getProjectTitle() {
		return projectTitle;
	}
	
	/**
	 * Function to set project title
	 * @param projectTitle project title
	 */
	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}
	
	/**
	 * Function to get twitter search tag
	 * @return twitter search tag
	 */
	public String getTwitterSearch() {
		return twitterSearch;
	}
	
	/**
	 * Function to set twitter search tag
	 * @param twitterSearch twitter search tag
	 */
	public void setTwitterSearch(String twitterSearch) {
		this.twitterSearch = twitterSearch;
	}
	
	/**
	 * Function to get project description
	 * @return project description
	 */
	public String getDecription() {
		return decription;
	}
	
	/**
	 * Function to set project description
	 * @param decription project description
	 */
	public void setDecription(String decription) {
		this.decription = decription;
	}
	
	/**
	 * Function to get card collection.
	 * @return card collection.
	 */
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	/**
	 * Function to set card collection
	 * @param cards card collection
	 */
	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
}
