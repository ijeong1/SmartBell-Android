package com.example.smartbell.barpath;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.smartbell.primitives.Moment;


import au.com.bytecode.opencsv.CSVReader;

public class BarPathTracker {

	public static final int X = 0;
	public static final int Y = 1;
	public static final int Z = 2;
	
	private ArrayList<Moment> moments;
	
	public BarPathTracker(String infile) {
		moments = generateMoments(getInputList(infile));
	}

	List getInputList(String infile){
		List myEntries = null;
		CSVReader reader;
		try {
			reader = new CSVReader(new FileReader(infile));
			myEntries = reader.readAll();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return myEntries;
	}

	ArrayList<Moment> generateMoments(List<String[]> myEntries){
		ArrayList<Moment> m = new ArrayList<Moment>();
		
		for(int i = 0; i < myEntries.size(); i++){
			m.add(new Moment(myEntries.get(i)));
		}
		
		return m;
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
		
		for(int i = 1; i < moments.size(); i++){
			t0 = moments.get(i).getTimestamp();
			dt = t0 - t;
			positions.add(pos);
			
			System.out.println("pos after: "+(i - 1)+" ("+positions.get(i-1)[X]+", "+positions.get(i-1)[Y]+", "+positions.get(i-1)[Z]+")");
			System.out.println("vel after: "+(i - 1)+" ("+vel[X]+", "+vel[Y]+", "+vel[Z]+")");
			System.out.println("acc after: "+(i - 1)+" ("+moments.get(i - 1).getAcc()[X]+", "+moments.get(i - 1).getAcc()[Y]+", "+moments.get(i - 1).getAcc()[Z]+")");
			System.out.println("dt: "+dt);
			vel[X] = (moments.get(i - 1).getAcc()[X] + moments.get(i).getAcc()[X]) / 2.0 * dt;
			vel[Y] = (moments.get(i - 1).getAcc()[Y] + moments.get(i).getAcc()[Y]) / 2.0 * dt;
			vel[Z] = (moments.get(i - 1).getAcc()[Z] + moments.get(i).getAcc()[Z]) / 2.0 * dt;
		
			pos[X] = (prevVel[X] + vel[X]) / 2 * dt;
			pos[Y] = (prevVel[Y] + vel[Y]) / 2 * dt;
			pos[Z] = (prevVel[Z] + vel[Z]) / 2 * dt;
			
			t = t0;
			prevVel = vel;
		}
		
		return positions;
	}
	
}
