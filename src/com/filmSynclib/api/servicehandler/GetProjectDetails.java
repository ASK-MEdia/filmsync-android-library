package com.filmSynclib.api.servicehandler;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.filmSynclib.api.constants.ApiConstants;
import com.filmSynclib.interfaces.ProjectDectectionListener;
import com.filmSynclib.interfaces.SessionReceivedListener;
import com.filmSynclib.model.Card;
import com.filmSynclib.model.Project;
import com.filmSynclib.model.Session;

/**
 * Class to get Project details for detected card
 * @author fingent
 *
 */
public class GetProjectDetails extends AsyncTask<Void, Void, Project>{

	String url; //Url to request project details
	int projectID; //Project identifier
	String response; //response from server
	ApiHandler apiHandler; //handle the http requests
	Project project; //object model object
	JSONObject projectJsonObject,projectDetails;
	JSONArray cards;
	ProjectDectectionListener projectDectectionListener; //listener executed after project detected 
	ArrayList<Card> projectCardList; //card collection under the project
	Session session;
	SessionReceivedListener sessionReceivedListener;
	boolean isProjectEmpty=false;
	boolean isSessionExpired=false;
	Card card;
	
	/**
	 * 
	 * @param card - card data
	 * @param projectDectectionListener - listener executed when project detected.
	 * @param sessionReceivedListener - session received after re-authentication.
	 * @param session - session data.
	 */
	public GetProjectDetails(Card card,ProjectDectectionListener projectDectectionListener,SessionReceivedListener sessionReceivedListener, Session session) {
		// TODO Auto-generated constructor stub
		apiHandler=new ApiHandler();
		this.card=card;
		this.projectID=Integer.parseInt(card.getCardProjectID());
		this.url=ApiHandler.getBaseUrl()+ApiConstants.GET_PROJECT_URL+"/"+projectID+"/"+session.getSessionID();
		//this.url=ApiConstants.GET_PROJECT_URL+"/"+projectID;
		this.projectDectectionListener=projectDectectionListener;
		this.sessionReceivedListener=sessionReceivedListener;
		this.session=session;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		project=new Project();
	}

	//Fetching the project details from server.
	@Override
	protected Project doInBackground(Void... arg0) {
		// TODO Auto-generated method stub

		//API call
		response=apiHandler.makeServiceCall(url);
		
		try {
			if(!response.equalsIgnoreCase(ApiConstants.BASE_URL_NOT_SET_WARNING_MSG)){ //base url and api secret is valid
				projectJsonObject= new JSONObject(response);
				project.setWarningMSG(projectJsonObject.getString(ApiConstants.EXIST_TAG));
				String session=projectJsonObject.getString(ApiConstants.SESSION_TAG);

				if(session.equalsIgnoreCase(ApiConstants.SESSION_ACTIVE_VALUE)){ //Session active
					
					String isCardExist=projectJsonObject.getString(ApiConstants.EXIST_TAG);
					if(isCardExist.equalsIgnoreCase(ApiConstants.EXIST_VALUE)){ //Project exist in server.
						
						projectDetails=projectJsonObject.getJSONObject(ApiConstants.PROJECT_TAG);
						cards=projectJsonObject.getJSONArray(ApiConstants.CARD_TAG);
						System.out.println("Card Length:- "+cards.length());
						project.setProjectID(projectDetails.getString(ApiConstants.PROJECT_ID_TAG));
						project.setProjectTitle(projectDetails.getString(ApiConstants.PROJECT_TITLE_TAG));
						project. setDecription(projectDetails.getString(ApiConstants.PROJECT_DESC_TAG));
						project.setTwitterSearch(projectDetails.getString(ApiConstants.PROJECT_TWITTER_TAG));

						projectCardList=new ArrayList<Card>();

						for(int i=0;i<cards.length();i++){ //fetching card details of project.
							Card card=new Card();
							card.setCardID(cards.getJSONObject(i).getString(ApiConstants.CARD_ID_TAG));
							card.setCardProjectID(project.getProjectID());
							card.setCardTitle(cards.getJSONObject(i).getString(ApiConstants.CARD_TITLE_TAG));
							//card.setCardTwitterSearch(cards.getJSONObject(i).getString(Constants.CARD_TWITTER_TAG));
							card.setCardContent(cards.getJSONObject(i).getString(ApiConstants.CARD_CONTENT_TAG));
							projectCardList.add(card);
						}
						System.out.println("Card Project Length:- "+projectCardList.size());
						project.setCards(projectCardList);

					}else if(isCardExist.equalsIgnoreCase(ApiConstants.NOT_EXIST_VALUE)){ //Project not exist in server.
						
						isProjectEmpty=true;
					}
					
				}else{ //session not active.
					isSessionExpired=true;
					
					System.out.println("Session Expired on GetProject!!!");
				}
			}else{ //base url and api secret is not valid
				project.setWarningMSG(response);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("EXCEPTION AT GETCARD ASYNC TASK");
		}

		return project;
	}

	@Override
	protected void onPostExecute(Project project) {
		// TODO Auto-generated method stub
		super.onPostExecute(project);
		try{
			if(isSessionExpired){ //Session expired
				sessionReceivedListener.onSessionExpiredWithProject(card);
				
			}else if(isProjectEmpty){// Project not exist in the server
				projectDectectionListener.onProjectDeleted(projectID);
			}
			else{ //valid project with active session detected.
				projectDectectionListener.onProjectDetected(project);
			}
		}catch(NullPointerException ne){
			ne.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
