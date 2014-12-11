package com.filmSynclib.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Class to manage preferences values.
 * @author fingent
 *
 */
public class FilmSyncPreferences {


	public static final String CLEAN_OFFSET="Clean_Offset"; //time interval at which recored buffer will be cleaned preference key

	public static final String FILMSYNC_PREFERENCE_FILE_NAME="FilmSyncPreference_File"; //preference file name
	
	public static final String FILMSYNC_SESSION_KEY="Filmsync_Session_Key"; //preference SessionID key
	public static final String FILMSYNC_SESSION_STATUS_KEY="Filmsync_Session_Status_Key"; //preference Session status key

	Context context;

	SharedPreferences preferences; //preference object
	SharedPreferences.Editor editor; //preference editor object

	/**
	 * Constructor
	 * @param context context at which preference file created.
	 */
	public FilmSyncPreferences(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
		preferences=context.getSharedPreferences(FILMSYNC_PREFERENCE_FILE_NAME,0);
		editor=preferences.edit();
	}


	/**
	 * Function to get clean offset value.
	 * @return clean offset value.
	 */

	public long getCleanOffset() {

		return preferences.getLong(CLEAN_OFFSET, 5200);

	}

	/**
	 * Function to set the clean offset value
	 * @param cleanOffset clean offset value to set
	 */
	public void setCleanOffset(long cleanOffset) {

		editor.putLong(CLEAN_OFFSET, cleanOffset);
		editor.commit();

	}
	
	/**
	 * Function to store the session code in preference file.
	 * @param sessionID session code
	 */
	public void putSessionID(String sessionID){
		editor.putString(FILMSYNC_SESSION_KEY, sessionID);
		editor.commit();
	}
	
	/**
	 * Function to get session code from preference file.
	 * @return session code from preference file.
	 */
	public String getSessionID(){
		return preferences.getString(FILMSYNC_SESSION_KEY, "");
	}
	
	/**
	 * Function to put session status in preference file
	 * @param sessionStatus session status.
	 */
	public void putSessionStatus(String sessionStatus){
		editor.putString(FILMSYNC_SESSION_STATUS_KEY, sessionStatus);
		editor.commit();
	}
	
	/**
	 * Function to get session status from preference file.
	 * @return session status from preference file.
	 */
	public String getSessionStatus(){
		return preferences.getString(FILMSYNC_SESSION_STATUS_KEY, "active");
	}
}
