package com.mikelady.smartbell.barpath;

import java.util.ArrayList;

import android.util.Log;

import com.mikelady.smartbell.primitives.Moment;

public class BarPathTracker {

	public static final int X = 0;
	public static final int Y = 1;
	public static final int Z = 2;
	
	private ArrayList<Moment> moments;
	
	public BarPathTracker(ArrayList<Moment> moments) {
		this.moments = moments;
	}


	public ArrayList<Moment> getMoments() {
		return moments;
	}

	public void setMoments(ArrayList<Moment> moments) {
		this.moments = moments;
	}
	
	public ArrayList<Double[]> determinePosition(){
		/* https://stackoverflow.com/questions/6645126/distance-moved-by-accelerometer
		 * 	init:
		    loc = {0, 0, 0}    ; location of object on screen
		    vel = {0, 0, 0}    ; velocity of object on screen
		    t = 0              ; last time measured
		
		step:
		    t0 = time          ; get amount of time since last measurement
		    dt = t0 - t
		    accel = readAccelerometer()
		    vel += accel * dt  ; update velocity accel = (acc[i-1] + acc[i])/2 +=?
		    loc += vel * dt    ; update position
		    displayObjectAt(loc)
		 */
		ArrayList<Double[]> positions = new ArrayList<Double[]>();
		
		Double[] pos = {0.0, 0.0, 0.0};
		Double[] vel = {0.0, 0.0, 0.0};
		Double[] prevVel = {0.0, 0.0, 0.0};
		long t = 0;
		long t0;
		long dt;
		
		for(int i = 5; i < moments.size(); i++){
			t0 = moments.get(i).getTimestamp();
			dt = t0 - t;
			positions.add(pos.clone());
			
//			Log.d("ml","pos after: "+(i - 1)+" ("+positions.get(i-1)[X]+", "+positions.get(i-1)[Y]+", "+positions.get(i-1)[Z]+")");
//			Log.d("ml","vel after: "+(i - 1)+" ("+vel[X]+", "+vel[Y]+", "+vel[Z]+")");
//			Log.d("ml","acc after: "+(i - 1)+" ("+moments.get(i - 1).getLinAcc()[X]+", "+moments.get(i - 1).getLinAcc()[Y]+", "+moments.get(i - 1).getLinAcc()[Z]+")");
//			Log.d("ml","dt: "+dt);
			vel[X] = (moments.get(i - 1).getLinAcc()[X] + moments.get(i).getLinAcc()[X]) / 2.0 * dt;
			vel[Y] = (moments.get(i - 1).getLinAcc()[Y] + moments.get(i).getLinAcc()[Y]) / 2.0 * dt;
			vel[Z] = (moments.get(i - 1).getLinAcc()[Z] + moments.get(i).getLinAcc()[Z]) / 2.0 * dt;
		
			pos[X] = (prevVel[X] + vel[X]) / 2 * dt;
			pos[Y] = (prevVel[Y] + vel[Y]) / 2 * dt;
			pos[Z] = (prevVel[Z] + vel[Z]) / 2 * dt;
			
			t = t0;
			prevVel = vel;
		}
		
		for(Double[] d : positions){
			Log.d("ml", "d[0] = "+ d[0] +" d[1] = " + d[1] + " d[2] = "+ d[2]);
		}
		
		return positions;
	}
	
}
