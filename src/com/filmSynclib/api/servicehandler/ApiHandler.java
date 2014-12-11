package com.filmSynclib.api.servicehandler;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.filmSynclib.api.constants.ApiConstants;

/**
 * Class to handle http requests.
 * @author fingent
 *
 */
public class ApiHandler {

	static String response = null;
	/*public final static int GET = 1;
	public final static int POST = 2;*/

	private static String baseUrl;
	private static String apiSecret;
	private static String session;
	private static String sessionStats;


	public ApiHandler() {
	}

	/**
	 * Making service call
	 * @url - url to make request
	 * */
	public String makeServiceCall(String url) {
		return this.makeServiceCall(url,null);
	}

	/**
	 * Function to set  the base url
	 * @param bUrl - url
	 */
	public static void setBaseUrl(String bUrl){

		String baseUrl=bUrl;
		baseUrl=baseUrl.trim();

		//base url not end with '/' thwn add '/' at end.
		if(!(baseUrl.charAt(baseUrl.length()-1)=='/')){
			baseUrl+='/';
		}

		ApiHandler.baseUrl=baseUrl;
	}

	/**
	 * Function to set the Api secret code
	 * @param secret
	 */
	public static void setApiSecret(String secret){
		ApiHandler.apiSecret=secret;
	}

	/**
	 * Function to get the base url
	 * @return base url
	 */
	public static String getBaseUrl(){
		return ApiHandler.baseUrl;
	}

	/**
	 * Function to get api secret code
	 * @return api secret code
	 */
	public static String getApiSecret(){
		return ApiHandler.apiSecret;
	}

	/**
	 * Function to get the session code
	 * @return
	 */
	public static String getSession() {
		return session;
	}

	/**
	 * Function to set the session code
	 * @param session session code
	 */
	public static void setSession(String session) {
		ApiHandler.session = session;
	}

	/**
	 * Function to get session status
	 * @return session status.
	 */
	public static String getSessionStats() {
		return sessionStats;
	}

	/**
	 * Function to set the session status
	 * @param sessionStats session status.
	 */
	public static void setSessionStats(String sessionStats) {
		ApiHandler.sessionStats = sessionStats;
	}


	/**
	 * Making service call
	 * @url - url to make request
	 * @params - http request params
	 * @return response from server.
	 * */
	public String makeServiceCall(String url, List<NameValuePair> params) {
		try {

			if(baseUrl!=null&&apiSecret!=null){
				// http client
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpEntity httpEntity = null;
				HttpResponse httpResponse = null;

				HttpGet httpGet = new HttpGet(url);

				httpResponse = httpClient.execute(httpGet);

				httpEntity = httpResponse.getEntity();
				response = EntityUtils.toString(httpEntity);

			}else{
				response=ApiConstants.BASE_URL_NOT_SET_WARNING_MSG;

			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//return response.
		return response;

	}

}