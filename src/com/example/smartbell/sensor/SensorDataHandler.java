package com.example.smartbell.sensor;

import java.util.ArrayList;

import com.example.smartbell.activity.MainActivity;
import com.example.smartbell.primitives.Moment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

// code from yei technologies demo project
public class SensorDataHandler extends Handler {

	private boolean keep_going = false;

//	static CSVWriter csv;
	
	ArrayList<Moment> moments = new ArrayList<Moment>();
	Float[] euler;
	Float[] linacc;
	long unixTime;
	
	//refactor this later
	@Override
    public void handleMessage(Message msg) {
//		if(csv == null)
//			csv = new CSVWriter(MainActivity.ctx);
		
		try {
			euler = TSSBTSensor.getInstance().getEulerAngles();
			linacc = TSSBTSensor.getInstance().getLinAcc();
			unixTime = System.currentTimeMillis();
			moments.add(new Moment(unixTime, euler, linacc));
			Log.d("ML", "logging in memory");
			
		} catch (Exception e) {
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
			sendMessage(tmp_message);//250
		}
		else if(msg.what == 1)
		{
			if (keep_going){
				try {
					//Call yourself again in a bit*/
					sendMessage(obtainMessage(1,0,0));
				} catch (Exception e) {
					return;
				}

			}
		}
    }

	public ArrayList<Moment> getMoments() {
		return moments;
	}

	public void setMoments(ArrayList<Moment> moments) {
		this.moments = moments;
	}

}
