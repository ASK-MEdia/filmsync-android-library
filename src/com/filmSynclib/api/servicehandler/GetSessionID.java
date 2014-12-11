package com.filmSynclib.api.servicehandler;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.filmSynclib.api.constants.ApiConstants;
import com.filmSynclib.interfaces.SessionReceivedListener;
import com.filmSynclib.model.Card;
import com.filmSynclib.model.Session;

/**
 * Class to handle session 
 * @author fingent
 *
 */
public class GetSessionID extends AsyncTask<Void, Void, Session> {

	String url; //session management url.
	String response; // response from server
	JSONObject sessionJsonObject; // store the session response as json data.
	Session session; //Session object
	ApiHandler apiHandler; //to handle http request
	SessionReceivedListener sessionReceivedListener; //listener listen session data receive.
	String codeNo=null; //Code number 
	Card card=null; //card details
	Integer projectID=null;//Project identifier
	
	/**
	 * Constructor
	 * @param sessionReceivedListener listener executed when session detected
	 */
	public GetSessionID(SessionReceivedListener sessionReceivedListener){
		apiHandler=new ApiHandler();
		this.url=ApiHandler.getBaseUrl()+ApiConstants.GET_SESSION+ApiHandler.getApiSecret();
		this.sessionReceivedListener=sessionReceivedListener;
	}

	/**
	 * Constructor
	 * @param sessionReceivedListener listener executed when session detected
	 * @param codeNo detected code, lost the session when fetching data for code.
	 */
	public GetSessionID(SessionReceivedListener sessionReceivedListener,String codeNo){
		apiHandler=new ApiHandler();
		this.url=ApiHandler.getBaseUrl()+ApiConstants.GET_SESSION+ApiHandler.getApiSecret();
		this.sessionReceivedListener=sessionReceivedListener;
		this.codeNo=codeNo;
	}

	/**
	 * Constructor
	 * @param sessionReceivedListener - listening session data received.
	 * @param card - card data.
	 */
	public GetSessionID(SessionReceivedListener sessionReceivedListener,Card card){
		apiHandler=new ApiHandler();
		this.url=ApiHandler.getBaseUrl()+ApiConstants.GET_SESSION+ApiHandler.getApiSecret();
		this.sessionReceivedListener=sessionReceivedListener;
		this.card=card;
		this.projectID=Integer.parseInt(card.getCardProjectID());
	}
	@Override
	protected Session doInBackground(Void... params) {
		// TODO Auto-generated method stub
		session=new Session();
		
		//API call
		response=apiHandler.makeServiceCall(url);
		
		try {
			if(!response.equalsIgnoreCase(ApiConstants.BASE_URL_NOT_SET_WARNING_MSG)){ //base url and api secret is valid
				
				sessionJsonObject= new JSONObject(response);
				session.setSessionStatus(sessionJsonObject.getString(ApiConstants.SESSION_STATUS_TAG)); 
				
				if(session.getSessionStatus().equalsIgnoreCase(ApiConstants.SESSION_ACTIVE_VALUE)){ //Session active
					session.setSessionID(sessionJsonObject.getString(ApiConstants.SESSION_ID_TAG));
					session.setWarningMSG(sessionJsonObject.getString(ApiConstants.SESSION_STATUS_TAG));
				}
			}else{
				session.setWarningMSG(response);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return session;
	}

	@Override
	protected void onPostExecute(Session session) {
		// TODO Auto-generated method stub
		super.onPostExecute(session);

		try{

			if(codeNo!=null){ //code not null
				sessionReceivedListener.onSessionReceivedForCard(session, codeNo);
				
			}else if(projectID!=null){ //project number card not null
				sessionReceivedListener.onSessionReceivedForProject(session, card);
				
			}else{ 
				sessionReceivedListener.onSessionReceived(session);
			}

		}catch(NullPointerException ne){
			ne.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
