package com.mikelady.smartbell.sensor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
	HashMap<Long, byte[]> timestamp_bytes_map = new HashMap<Long, byte[]>();
	Float[] quat;
	Float[] linacc;
	Float[] compass;
	long unixTime;
	Context context;

	public SensorDataHandler(int mode, Context context){
		super();
		this.context = context;
		try {
//			TSSBTSensor.getInstance().setTareCurrentOrient();
			TSSBTSensor.getInstance().setFilterMode(mode);
			TSSBTSensor.getInstance().setupStreaming();

			keep_going = true;
			/*			int i = 0;
			Log.d("ml", "start timestamp: "+System.currentTimeMillis());
			while(keep_going && i < 100){
				quat = TSSBTSensor.getInstance().getEulerAngles();
				linacc = TSSBTSensor.getInstance().getLinAcc();
				unixTime = System.currentTimeMillis();
				moments.add(new Moment(unixTime, quat, linacc));
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

		//Check if we are supposed to keep going or not
		if (msg.what == -1)
		{
			keep_going = false;
			try {
				TSSBTSensor.getInstance().stopStreaming();
				Log.d("SensorDataHandler", "Called stopStreaming");
				//						TSSBTSensor.getInstance().close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte[] bytes, quat_bytes, linacc_bytes, compass_bytes;
			int j;
			//process collected bytes in timestamp_bytes_map and add them to moments
			Long[] timestamps = timestamp_bytes_map.keySet().toArray(new Long[timestamp_bytes_map.keySet().size()]);
			Arrays.sort(timestamps);
			for(int i =  0; i < timestamps.length; i++){
				bytes = timestamp_bytes_map.get(timestamps[i]);
				quat_bytes = new byte[TSSBTSensor.TSS_QUAT_LEN];
				linacc_bytes = new byte[TSSBTSensor.TSS_LIN_ACC_LEN];
				compass_bytes = new byte[TSSBTSensor.TSS_COMPASS_LEN];
				for(j = 0; j < bytes.length; j++){
					if(j < TSSBTSensor.TSS_QUAT_LEN){
						quat_bytes[j] = bytes[j];
					}
					else if(j > TSSBTSensor.TSS_QUAT_LEN &&
							j < TSSBTSensor.TSS_QUAT_LEN + TSSBTSensor.TSS_LIN_ACC_LEN){ //also include raw values
						linacc_bytes[j - TSSBTSensor.TSS_QUAT_LEN] = bytes[j];
					}
					else{
						compass_bytes[j - TSSBTSensor.TSS_QUAT_LEN - TSSBTSensor.TSS_LIN_ACC_LEN] = bytes[j];
					}
				}
				try {
					quat = TSSBTSensor.getInstance().binToFloat(quat_bytes);
					linacc = TSSBTSensor.getInstance().binToFloat(linacc_bytes);
					compass = TSSBTSensor.getInstance().binToFloat(compass_bytes);
					moments.add(new Moment(timestamps[i], quat, linacc, compass));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		else if (msg.what == 287)
		{
			keep_going = true;
			//Call yourself again in a bit
			Message tmp_message = new Message();
			tmp_message.what = 1;
//			Log.d("SensorDataHandler", "on 287 message");
			try {
				Log.d("SensorDataHandler", "Going to call startStreaming");
				TSSBTSensor.getInstance().startStreaming();
				Log.d("SensorDataHandler", "Called startStreaming");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendMessage(tmp_message);//250
		}
		else if(msg.what == 1)
		{
			try {
				Log.d("SensorDataHandler", "before read");
				byte[] stream_data = TSSBTSensor.getInstance().read(com.mikelady.smartbell.sensor.TSSBTSensor.TSS_QUAT_LEN +
						com.mikelady.smartbell.sensor.TSSBTSensor.TSS_LIN_ACC_LEN +
						com.mikelady.smartbell.sensor.TSSBTSensor.TSS_COMPASS_LEN);
				//			quat = TSSBTSensor.getInstance().getEulerAngles();
				//			linacc = TSSBTSensor.getInstance().getLinAcc();
				//			compass =

				unixTime = System.currentTimeMillis();
				timestamp_bytes_map.put(unixTime, stream_data);
				Log.d("SensorDataHandler", "steam_data[0]: "+(byte)stream_data[0]);
				//			moments.add(new Moment(unixTime, quat, linacc));
				//			Log.d("SensorDataHandler", ""+moments.get(moments.size()-1).toString());


			} catch (Exception e) {
				e.printStackTrace();
			}
			
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
