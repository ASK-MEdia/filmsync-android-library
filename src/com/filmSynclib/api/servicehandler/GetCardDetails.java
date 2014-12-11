package com.filmSynclib.api.servicehandler;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.filmSynclib.api.constants.ApiConstants;
import com.filmSynclib.interfaces.CardDetectionListener;
import com.filmSynclib.interfaces.SessionReceivedListener;
import com.filmSynclib.model.Card;
import com.filmSynclib.model.Session;

/**
 * Class to get card details for detected card-id.
 * @author fingent
 *
 */
public class GetCardDetails extends AsyncTask<Void, Void, Card>{

	String url; //url to request card details
	String cardNo; //card identifier
	String response; // response from server
	ApiHandler apiHandler; //to handle http request
	Card card; //card object
	JSONObject cardJsonObject;
	CardDetectionListener cardDetectionListener;
	SessionReceivedListener sessionReceivedListener;
	Session session;
	boolean isSessionExpired=false;
	boolean isCardEmpty=false;
	
	/**
	 * Constructor
	 * @param cardNo - card identifier
	 * @param cardDetectionListener - listener executed after card detected.
	 * @param sessionReceivedListener - listener executed after session reauthenticated.
	 * @param session - session data
	 */
	public GetCardDetails(String cardNo,CardDetectionListener cardDetectionListener,SessionReceivedListener sessionReceivedListener,Session session) {
		// TODO Auto-generated constructor stub
		apiHandler=new ApiHandler();
		this.cardNo=cardNo;
		this.url=ApiHandler.getBaseUrl()+ApiConstants.GET_CARD_URL+"/"+cardNo+"/"+session.getSessionID();
		this.cardDetectionListener=cardDetectionListener;
		this.sessionReceivedListener=sessionReceivedListener;
		this.session=session;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

	}
	
	//Fetching the card details from server.
	@Override
	protected Card doInBackground(Void... params) {
		// TODO Auto-generated method stub
		card=new Card();
		//API call
		response=apiHandler.makeServiceCall(url);
		
		try {
			if(!response.equalsIgnoreCase(ApiConstants.BASE_URL_NOT_SET_WARNING_MSG)){ //base url and api secret is valid 
				cardJsonObject= new JSONObject(response);

				String session=cardJsonObject.getString(ApiConstants.SESSION_TAG);

				if(session.equalsIgnoreCase(ApiConstants.SESSION_ACTIVE_VALUE)){ //Session active
					
					String isCardExist=cardJsonObject.getString(ApiConstants.EXIST_TAG);
					
					if(isCardExist.equalsIgnoreCase(ApiConstants.EXIST_VALUE)){ //card exist in server.
						
						card.setCardID(cardJsonObject.getString(ApiConstants.CARD_ID_TAG));
						card.setCardProjectID(cardJsonObject.getString(ApiConstants.PROJECT_ID_TAG));
						card.setCardTitle(cardJsonObject.getString(ApiConstants.CARD_TITLE_TAG));
						card.setCardTwitterSearch(cardJsonObject.getString(ApiConstants.CARD_TWITTER_TAG));
						card.setCardContent(cardJsonObject.getString(ApiConstants.CARD_CONTENT_TAG));
						card.setWarningMSG(cardJsonObject.getString(ApiConstants.EXIST_TAG));
						
					}else if(isCardExist.equalsIgnoreCase(ApiConstants.NOT_EXIST_VALUE)){ //card not exist in server.
						
						isCardEmpty=true;
					}
				}else{ //session not active.
					isSessionExpired=true;
					card.setWarningMSG(ApiConstants.SESSION_INACTIVE_VALUE);
					System.out.println("Session Expired on GetCard!!!");
				}

			}else{ //base url and api secret is not valid
				card.setWarningMSG(response);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("EXCEPTION AT GETCARD ASYNC TASK");
		}

		return card;
	}

	@Override
	protected void onPostExecute(Card card) {
		// TODO Auto-generated method stub
		super.onPostExecute(card);
		try{
			
			if(isCardEmpty){ //card not exist in server
				
				cardDetectionListener.onCardDeleted(cardNo); //delete card from local DB if exist.
				
			}else if(isSessionExpired){ //session expired
				
				sessionReceivedListener.onSessionExpiredWithCard(cardNo);
				
			}else{ //valid card detected with active session id.
				
				cardDetectionListener.onCardDetected(card);
				
			}
			
		}catch(NullPointerException ne){
			ne.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
