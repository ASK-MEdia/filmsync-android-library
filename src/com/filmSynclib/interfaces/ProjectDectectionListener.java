package com.filmSynclib.interfaces;

import com.filmSynclib.model.Project;

/**
 * interface implement where we need to listen project detection
 * @author fingent
 *
 */
public interface ProjectDectectionListener {

	/**
	 * function executes when project detected
	 * @param project project details
	 */
	public void onProjectDetected(Project project);
	
	/**
	 * Function to execute when project deleted
	 * @param projectID project identifier
	 */
	public void onProjectDeleted(int projectID);
}
