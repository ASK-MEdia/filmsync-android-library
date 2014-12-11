package com.filmSynclib;

import android.os.AsyncTask;
import android.util.Log;

import com.filmSynclib.model.DataBlock;
import com.filmSynclib.model.Recognizer;
import com.filmSynclib.model.Spectrum;
import com.filmSynclib.model.StatelessRecognizer;

/**
 * Class to recognize the keys from detected audio
 * @author fingent
 *
 */

public class RecognizerTask extends AsyncTask<Void, Object, Void> {
	
	//private BlockingQueue<DataBlock> blockingQueue; //Queue to store recorded audio
	private Recognizer recognizer; //Recognizer model class object
	Controller controller; //controller class object
	
	/**
	 * Constructor
	 * @param controller controller class object
	 * @param blockingQueue recorded audio queue
	 */
	public RecognizerTask(Controller controller)//,BlockingQueue<DataBlock> blockingQueue) 
	{
		System.out.println("Inside RecognizerTask: ");
		this.controller = controller;
		//this.blockingQueue = blockingQueue;
		recognizer = new Recognizer();
	}
	
	//Fetch the recored stream from queue and detect the key  
	@Override
	public Void doInBackground(Void... params)
	{
		Log.d("Recognizer Task","started");
	
		while(Controller.isStarted)
		{
			try {
				//System.out.println("Queue Size in Recog:"+Controller.blockingQueue.size());
				DataBlock dataBlock = Controller.blockingQueue.take(); //retrieving recorded queue
								
				Spectrum spectrum = dataBlock.FFT();
				
				spectrum.normalize();				
				
				StatelessRecognizer statelessRecognizer = new StatelessRecognizer(spectrum);
				
				Character key = recognizer.getRecognizedKey(statelessRecognizer.getRecognizedKey());
				
				//publish the recognized key.
				publishProgress(spectrum, key);
				
			} catch (InterruptedException e) { //queue interrupted
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d("blockingQueue.take() Interrupted @Recognise Task", "Catched");
			}
		}
		return null ;
	}
	
	//update the detected key to controller.
	@Override
	public void onProgressUpdate(Object... progress) 
	{
		super.onProgressUpdate(progress);
		
		//Fetch the key
		Character key = (Character)progress[1];
		
		//update key to controller.
		controller.keyReady(key);
    }
	
	@Override
	protected void onCancelled(Void result) {
		// TODO Auto-generated method stub
		super.onCancelled(result);
	}
	
}
