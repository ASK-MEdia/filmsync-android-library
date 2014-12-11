package com.filmSynclib;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.filmSynclib.model.DataBlock;
import com.filmSynclib.preferences.FilmSyncPreferences;

/**
 * Class to record audio using microphone and store in queue
 * @author fingent
 *
 */
public class RecordTask extends AsyncTask<Void, Object, Void> {


	int frequency = 48000; //frequency

	int channelConfiguration = AudioFormat.CHANNEL_IN_MONO; //channel configuration

	int audioEncoding = AudioFormat.ENCODING_PCM_16BIT; //audio encoding format

	int blockSize = 1024; //block size of buffer

	//BlockingQueue<DataBlock> blockingQueue; //queue to store recorded audio

	static TelephonyManager telephonyManager; //telephony manager object

	FilmSyncPreferences filmSyncPreferences;

	/**
	 * Constructor
	 * @param context context where object created
	 * @param blockingQueue queue to store recorded audio
	 */
	public RecordTask(Context context){//, BlockingQueue<DataBlock> blockingQueue) {

		//this.blockingQueue = blockingQueue;
		//blockingQueue.clear();
		telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		filmSyncPreferences=new FilmSyncPreferences(context);
	}
	/**
	 * Function to get audio source 
	 * @return audio source constant
	 */

	public static int getAudioSource()
	{

		return MediaRecorder.AudioSource.CAMCORDER;  // Disable noise cancellation
	}

	//background task to fetch audio stream from audio source and save into queue.
	@Override
	protected Void doInBackground(Void... params) {
		Log.d("Record Task","started");
		System.out.println("doinbackground....");
		int bufferSize = AudioRecord.getMinBufferSize(frequency, channelConfiguration, audioEncoding); //get minimum buffer size

		//Instantiate the recorder.
		AudioRecord audioRecord = new AudioRecord(getAudioSource(), frequency, channelConfiguration, audioEncoding, bufferSize); //initialize the audio recorder object


		try { 

			short[] buffer = new short[blockSize];

			try{
				
				audioRecord.startRecording(); //start audio record

			}catch(IllegalStateException ise){ //Illegal State exception while recording
				Log.d("EXCEPTION @ audioRecord.startRecording", "Catched");
				ise.printStackTrace();
			}
			//System.out.println("Queue Size:"+Controller.blockingQueue.size());
			Controller.blockingQueue.clear();

			while(Controller.isStarted)
			{
				int bufferReadSize = audioRecord.read(buffer, 0, blockSize);

				DataBlock dataBlock = new DataBlock(buffer, blockSize, bufferReadSize);

				Controller.blockingQueue.put(dataBlock); //fill data blocks to queue
			}

		}catch (InterruptedException ie) {
			// TODO: handle exception
			ie.printStackTrace();
			Log.d("blockingQueue.take() Interrupted @Record Task", "Catched");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try{
			//Stop the audio record.
			audioRecord.stop();
			//Release the recorder.
			audioRecord.release();
			audioRecord=null;

		}catch(IllegalStateException ie){
			ie.printStackTrace();
		}
		catch (RuntimeException re) {
			// TODO: handle exception
			re.printStackTrace();
		}
		
		return null;
	}

	@Override
	protected void onCancelled(Void result) {
		// TODO Auto-generated method stub
		super.onCancelled(result);
	}
}