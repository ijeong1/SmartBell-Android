package com.example.smartbell;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

// code from yei technologies demo project
public class SensorDataHandler extends Handler {

	private boolean keep_going = false;

	static CSVWriter csv;
	
	//refactor this later
	@Override
    public void handleMessage(Message msg) {
		if(csv == null)
			csv = new CSVWriter(MainActivity.ctx);
		try {
//			csv.writeQuat(TSSBTSensor.getInstance().getFiltTaredOrientQuat());
/*			csv.writeEuler(TSSBTSensor.getInstance().getFiltTaredOrientEuler());
			csv.writeRot(TSSBTSensor.getInstance().getFiltTaredOrientRot());
			csv.writeAxis(TSSBTSensor.getInstance().getFiltTaredOrientAxis());
			csv.writeTwoVec(TSSBTSensor.getInstance().getFiltTaredOrientTwoVec());
			csv.writeDiff(TSSBTSensor.getInstance().differenceQuat());
			*/
			
//			csv.writeCorrectLin(TSSBTSensor.getInstance().getCorrectedLinAcc());
			csv.writeNorm(TSSBTSensor.getInstance().getNormalComponentData());
//			csv.writeNormGyro(TSSBTSensor.getInstance().getNormalGyroData());
//			csv.writeNormGyro(TSSBTSensor.getInstance().getNormalAccelData());
//			csv.writeCorrect(TSSBTSensor.getInstance().getCorrectedComponentData());
			
//			csv.writeRaw(TSSBTSensor.getInstance().getRawComponentData());
			Log.d("ML", "writing to csv!");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Check if we are supposed to keep going or not
		if (msg.what == -1)
		{
			keep_going = false;
		}
		else if (msg.what == 287 && keep_going == false)
		{
			keep_going = true;
			//Call yourself again in a bit
			Message tmp_message = new Message();
			tmp_message.what = 1;
			sendMessageDelayed(tmp_message, 1);//250
		}
		else if(msg.what == 1)
		{
			if (keep_going){
				float[] orient;
				try {
					//Call yourself again in a bit*/
					sendMessageDelayed(obtainMessage(1,0,0), 1);
				} catch (Exception e) {
					return;
				}

			}
		}
    }

}
