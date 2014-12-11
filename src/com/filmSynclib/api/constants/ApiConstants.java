package com.filmSynclib.api.constants;

/**
 * Class to save all service constants.
 * @author fingent
 *
 */
public class ApiConstants {
	
	public static final String GET_SESSION="api/handshake/"; //URL to get session id.
	public static final String GET_ALL_CARDS_URL="api/getallcards"; //URL to get all card details.
	public static final String GET_PROJECT_URL="api/getcardsforproject"; //URL to get all cards in a project.
	public static final String GET_CARD_URL="api/getacard"; //URL to get a card details.

	public static final String SESSION_ID_TAG="sessionid"; //session id field key in response.
	public static final String SESSION_STATUS_TAG="status"; //session id field key in response.
	public static final String SESSION_TAG="session"; //session field key in response.
	public static final String SESSION_ACTIVE_VALUE="active"; 
	public static final String SESSION_INACTIVE_VALUE="expired";
	
	public static final String EXIST_TAG="empty";
	public static final String EXIST_VALUE="no";
	public static final String NOT_EXIST_VALUE="yes";
	
	public static final String PROJECT_ID_TAG="project_id"; //Project id field key in response.
	public static final String PROJECT_TITLE_TAG="title"; //Project title field key in response.
	public static final String PROJECT_TWITTER_TAG="twittersearch";//Project twitter search filed key in response.
	public static final String PROJECT_DESC_TAG="description"; //Project description filed key in response.

	public static final String CARD_ID_TAG="card_id";//Card id filed key in response.
	public static final String CARD_TITLE_TAG="title"; //Card tile filed key in response. 
	public static final String CARD_TWITTER_TAG="twittersearch";//Card twitter search filed key in response.
	public static final String CARD_CONTENT_TAG="content";//Card content filed key in response.

	public static final String PROJECT_TAG="project"; //Project filed key in response.
	public static final String CARD_TAG="cards"; // Card array filed key in response.
	
	public static final String BASE_URL_NOT_SET_WARNING_MSG="Please set the BaseUrl and Apisecret!!"; //Warning message if Api secret and base url not set.


}
