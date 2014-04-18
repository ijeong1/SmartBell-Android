package com.mikelady.smartbell.sensor;

import java.util.ArrayList;

import com.mikelady.smartbell.activity.SelectWorkoutActivity;
import com.mikelady.smartbell.primitives.Moment;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

// code from yei technologies demo project
public class SensorDataHandler extends Handler {

	private boolean keep_going = false;

//	static CSVWriter csv;
	
	ArrayList<Moment> moments = new ArrayList<Moment>();
	Float[] euler;
	Float[] linacc;
	long unixTime;
	Context context;
	
	public SensorDataHandler(int mode, Context context){
		super();
		this.context = context;
		try {
			TSSBTSensor.getInstance().setTareCurrentOrient();
			TSSBTSensor.getInstance().setFilterMode(mode);
			keep_going = true;
/*			int i = 0;
			Log.d("ml", "start timestamp: "+System.currentTimeMillis());
			while(keep_going && i < 100){
				euler = TSSBTSensor.getInstance().getEulerAngles();
				linacc = TSSBTSensor.getInstance().getLinAcc();
				unixTime = System.currentTimeMillis();
				moments.add(new Moment(unixTime, euler, linacc));
//				Log.d("ML", "logging in memory");
				i++;
			}
			Log.d("ml", "end timestamp: "+unixTime);*/
			
			Toast.makeText(context, "Acquired Bluetooth Sensor", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//refactor this later
	@Override
    public void handleMessage(Message msg) {
//		if(csv == null)
//			csv = new CSVWriter(SelectWorkoutActivity.ctx);
		Log.d("SensorDataHandler", "msg.what: "+msg.what);
		Log.d("SensorDataHandler", "keep_going: "+keep_going);
		try {
			euler = TSSBTSensor.getInstance().getEulerAngles();
			linacc = TSSBTSensor.getInstance().getLinAcc();
			unixTime = System.currentTimeMillis();
			moments.add(new Moment(unixTime, euler, linacc));
			Log.d("SensorDataHandler", ""+moments.get(moments.size()-1).toString());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Check if we are supposed to keep going or not
		if (msg.what == -1)
		{
			keep_going = false;
			try {
//				TSSBTSensor.getInstance().close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (msg.what == 287)
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
