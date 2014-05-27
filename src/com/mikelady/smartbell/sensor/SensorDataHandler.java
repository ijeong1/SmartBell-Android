package com.mikelady.smartbell.sensor;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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
	Float[] corrected;
	Float[] raw;
	long unixTime;
	Context context;
	
	int dataLength = com.mikelady.smartbell.sensor.TSSBTSensor.TSS_QUAT_LEN +
	com.mikelady.smartbell.sensor.TSSBTSensor.TSS_LIN_ACC_LEN +
//	com.mikelady.smartbell.sensor.TSSBTSensor.TSS_COMPASS_LEN+
	com.mikelady.smartbell.sensor.TSSBTSensor.TSS_CORRECTED_LEN+
	com.mikelady.smartbell.sensor.TSSBTSensor.TSS_RAW_LEN;

	public SensorDataHandler(int mode, Context context){
		super();
		this.context = context;
		try {
//			TSSBTSensor.getInstance().setTareCurrentOrient();
			TSSBTSensor.getInstance().setFilterMode(mode);
			TSSBTSensor.getInstance().setupStreaming();

			keep_going = true;

			Toast.makeText(context, "Acquired Bluetooth Sensor", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	@Override
	public void handleMessage(Message msg) {
		//		if(csv == null)
		//			csv = new CSVWriter(SelectWorkoutActivity.ctx);
//		Log.d("SensorDataHandler:handleMessage()", "msg.what: "+msg.what);
//		Log.d("SensorDataHandler:handleMessage()", "keep_going: "+keep_going);

		//Check if we are supposed to keep going or not
		if (msg.what == -1)
		{
			keep_going = false;
			try {
//				TSSBTSensor.getInstance().stopStreaming();
				Log.d("SensorDataHandler:handleMessage()", "Called stopStreaming");
				//						TSSBTSensor.getInstance().close();
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
//			Log.d("SensorDataHandler", "on 287 message");
			try {
				Log.d("SensorDataHandler:handleMessage()", "Going to call startStreaming");
				TSSBTSensor.getInstance().startStreaming();
				Log.d("SensorDataHandler:handleMessage()", "Called startStreaming");
				Log.d("SensorDataHandler:handleMessage()","Expected length of data: "+dataLength);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendMessage(tmp_message);//250
		}
		else if(msg.what == 1)
		{
			try {
//				Log.d("SensorDataHandler:handleMessage()", "before read");
				
				byte[] stream_data = TSSBTSensor.getInstance().read(dataLength);
				
				//			quat = TSSBTSensor.getInstance().getEulerAngles();
				//			linacc = TSSBTSensor.getInstance().getLinAcc();
				//			compass =

				unixTime = System.currentTimeMillis();
				timestamp_bytes_map.put(unixTime, stream_data);
//				Log.d("SensorDataHandler", "steam_data[0]: "+(byte)stream_data[0]);
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
		if(moments.isEmpty()){
			byte[] bytes, quat_bytes, linacc_bytes, corrected_bytes, raw_bytes;//compass_bytes,
			int i, j;
			//process collected bytes in timestamp_bytes_map and add them to moments
			Long[] timestamps = timestamp_bytes_map.keySet().toArray(new Long[timestamp_bytes_map.keySet().size()]);
			Arrays.sort(timestamps);
			Long start = timestamps[0];
			Long end = timestamps[timestamps.length-1];
			Long time = end - start;
			Log.d("SensorDataHandler:getMoments()", "Sensor ran for "+time+" ms");
			Log.d("SensorDataHandler:getMoments()", "Sensor collected "+timestamps.length+" moments");
			Double momentsPerSecond = timestamps.length / (time / 1000.0);
			Log.d("SensorDataHandler:getMoments()", "Data rate is "+ momentsPerSecond +" moments/second");
			for(i =  0; i < timestamps.length; i++){
				bytes = timestamp_bytes_map.get(timestamps[i]);
				
				ByteBuffer bb = ByteBuffer.wrap(bytes);
				bb.order(ByteOrder.nativeOrder());
				
				quat_bytes = new byte[TSSBTSensor.TSS_QUAT_LEN];
				bb.get(quat_bytes, 0, TSSBTSensor.TSS_QUAT_LEN);
				
				linacc_bytes = new byte[TSSBTSensor.TSS_LIN_ACC_LEN];
				bb.get(linacc_bytes, TSSBTSensor.TSS_QUAT_LEN, TSSBTSensor.TSS_LIN_ACC_LEN);
				
//				compass_bytes = new byte[TSSBTSensor.TSS_COMPASS_LEN];
				
				corrected_bytes = new byte[TSSBTSensor.TSS_CORRECTED_LEN];
				bb.get(corrected_bytes, TSSBTSensor.TSS_QUAT_LEN + TSSBTSensor.TSS_LIN_ACC_LEN, TSSBTSensor.TSS_CORRECTED_LEN);
				
				raw_bytes = new byte[TSSBTSensor.TSS_RAW_LEN];
				bb.get(raw_bytes, TSSBTSensor.TSS_QUAT_LEN + TSSBTSensor.TSS_LIN_ACC_LEN + TSSBTSensor.TSS_CORRECTED_LEN, TSSBTSensor.TSS_RAW_LEN);
				
				try {
					
					quat = TSSBTSensor.getInstance().binToFloat(quat_bytes);
					linacc = TSSBTSensor.getInstance().binToFloat(linacc_bytes);
//					compass = TSSBTSensor.getInstance().binToFloat(compass_bytes);
					corrected = TSSBTSensor.getInstance().binToFloat(corrected_bytes);
					raw = TSSBTSensor.getInstance().binToFloat(raw_bytes);
					moments.add(new Moment(timestamps[i], quat, linacc, corrected, raw));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return moments;
	}

	public void setMoments(ArrayList<Moment> moments) {
		this.moments = moments;
	}

}
