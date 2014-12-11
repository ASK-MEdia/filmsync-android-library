package com.filmSynclib;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.filmSynclib.R;
import com.filmSynclib.interfaces.CodeDetectedListener;
import com.filmSynclib.interfaces.DetectionStartStopListener;
import com.filmSynclib.model.DataBlock;
import com.filmSynclib.preferences.FilmSyncPreferences;


/**
 * Class to control the record and recognize processes.
 * @author fingent
 *
 */

public class Controller implements DetectionStartStopListener
{
	private static Character lastValue; //last key of detected code

	public static boolean isStarted=false; //listening started or not.
	static String codeText="";//detected code

	Context context; 
	private RecordTask recordTask;	//recordTask object
	private RecognizerTask recognizerTask;	//recognizeTask object

	public static BlockingQueue<DataBlock> blockingQueue; //Queue to store recorded stream

	CodeDetectedListener detectedListener; //Code detection listener

	Handler codeDectectionHandler;

	FilmSyncPreferences filmSyncPreferences;

	private boolean codeHeadDetected=false;

	public static final long DETECTION_TIME_IN_MILLISECONDS=5200;

	/**
	 * Constructor
	 * @param context 
	 * @param detectedListener
	 */

	public Controller(Context context1,CodeDetectedListener detectedListener)
	{
		this.context=context1;
		this.detectedListener=detectedListener;
		blockingQueue = new LinkedBlockingQueue<DataBlock>();
		codeDectectionHandler=new Handler();
		filmSyncPreferences=new FilmSyncPreferences(context);
		filmSyncPreferences.setCleanOffset(DETECTION_TIME_IN_MILLISECONDS);

	}


	/**
	 * Function to add detected key from recognizerTask to codeText variable 
	 * @param key detected key from recognizerTask
	 */
	public void keyReady(char key) 
	{
		if(key != ' '){ //key not a space character.
			System.out.println("KEY:- "+key);
			if(lastValue != key)
			{
				if(key=='h'||key=='H'){ //header 'H/h'detected.
					codeText="";//clear the code

					//set code head detected flag to true.
					codeHeadDetected=true;
					Log.d("Handler", "H detected");
					//call code head detected listener.
					detectedListener.onCodeHeaderDetected("Listening...");

					//handler to check the detected code after 5.2 seconds.
					codeDectectionHandler.postDelayed(new Runnable() {

						public void run() {
							// TODO Auto-generated method stub

							//get the code.
							String filteredCode=filterCode(codeText);

							if( codeText.length()>0 && !(filteredCode.length()==12) ){//a partial code detected.
								//Call code missed listener.
								detectedListener.onCodeMissed();
							}
							//clear the code.
							codeText="";

							//set code head detected flag to false.
							codeHeadDetected=false;

							//remove the handler to check the detected code after 5.2 seconds.
							codeDectectionHandler.removeCallbacks(this);

							Log.d("Handler", "stoped");
						}
					}, filmSyncPreferences.getCleanOffset());
				}

				if(codeHeadDetected){//code head detected then add key to code text
					codeText+=key;
				}

				if( (key=='t'||key=='T') && 
						filterCode(codeText).length()==12 ){ //Detected Terminating character 't' and detected code length is 12.

					System.out.println("Key detected: "+key);

					//Toast.makeText(context, "Code from t:-"+filterCode(codeText), Toast.LENGTH_SHORT).show();
					//call code detected listener.
					detectedListener.onCodeDetected(filterCode(codeText));
					//clear the code.
					codeText="";

					//set code head detected flag to false.
					codeHeadDetected=false;

				}else if(filterCode(codeText).length()==12){ //Detected code has 12 characters 

					//Toast.makeText(context, "Code:-"+filterCode(codeText), Toast.LENGTH_SHORT).show();

					//CAll code detected listener.
					detectedListener.onCodeDetected(filterCode(codeText));
					//clear the code text.
					codeText="";
					//set code head detected flag to false.
					codeHeadDetected=false;
				}
			}
		}
		//assign key to last value.
		lastValue = key;
	}

	/**
	 * Starts the recordTask and recognizerTask to record and detect.
	 */

	public void onStartListerning() {
		// TODO Auto-generated method stub\
		System.out.println("onStartListerning");
		if(hasMicrophone()){ //checks the availability of microphone

			if(!isStarted)//listening not started.
			{
				lastValue = ' ';
				//instantiate the recognize task
				recognizerTask = new RecognizerTask(Controller.this);
				//instantiate the record task
				recordTask = new RecordTask(context);
				try{

					/*
					//instantiate the record task
					recordTask = new RecordTask(context);//,blockingQueue);
					 */

					//instantiate the recognize task
					//,blockingQueue);


					//Start the record task.
					recordTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				}catch(IllegalStateException ise){
					ise.printStackTrace();
				}

				try{
					//Start recognize task.
					recognizerTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				}catch(IllegalStateException ise){
					ise.printStackTrace();
				}


				isStarted=true;
			}
		}else{ // Microphone not available

			Toast.makeText(context, R.string.microphone_not_found, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Stop the record and recognize task and set started variable to false
	 */
	public void onStopListerning() {
		// TODO Auto-generated method stub


		if(isStarted) //listening started.
		{
			System.out.println("recordTask and recognizerTask are trying to ccancel");
			try{
				if(!recognizerTask.isCancelled()){ //recognize task not cancelled/stopped. 

					//cancel the recognize task.
					recognizerTask.cancel(true);
					System.out.println("recognizerTask canceled");

				}else{//recognize task already cancelled/stopped.
					System.out.println("Recognize task already cancelled");
				}
			}catch(NullPointerException ne){

				ne.printStackTrace();
				isStarted = false;

			}catch (Exception e) {
				// TODO: handle exception

				e.printStackTrace();
				isStarted = false;
			}

			try{

				if(!recordTask.isCancelled()){//record task not cancelled/stopped.

					//record task cancelled.
					recordTask.cancel(true);
					System.out.println("recordTask canceled");

				}else{//record task cancelled/stopped.
					System.out.println("Record task already cancelled");
				}

				//Clear queue. 
				blockingQueue.clear();

				//set stared flag to false. 
				isStarted = false;
			}catch(NullPointerException ne){

				ne.printStackTrace();
				isStarted = false;

			}catch (Exception e) {
				// TODO: handle exception

				e.printStackTrace();
				isStarted = false;
			}
		}
	}

	/**
	 * Filter extra characters like header-t, separator-s and Terminator-t from detected code. 
	 * @param code code contains extra characters.
	 * @return Filtered code.
	 */
	public static String filterCode(String code){

		String filteredText="";
		for(int i=0;i<code.length();i++){

			/**
			 * Filter the additional character(S/s - separator, H/h- header, T/t - terminator).
			 */
			if( code.charAt(i)!='t' && code.charAt(i)!='T' &&
					code.charAt(i)!='h' && code.charAt(i)!='H' &&
					code.charAt(i)!='s' && code.charAt(i)!='S')
			{
				filteredText+=code.charAt(i);
			}

		}

		// return the filtered code.
		return filteredText;
	}

	/**
	 * Function to check the availability of Microphone
	 * @return true, if microphone available
	 */
	protected boolean hasMicrophone() {
		PackageManager pmanager = context.getPackageManager();
		return pmanager.hasSystemFeature(
				PackageManager.FEATURE_MICROPHONE);
	}

}
