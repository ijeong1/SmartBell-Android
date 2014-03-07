package com.mikelady.smartbell.primitives;

import java.util.ArrayList;

public class Athlete {
	
	
//	public final static int MAX_PRESS = 0;
//	public final static int MAX_BENCH = 1;
//	public final static int MAX_BACK_SQUAT = 2;

	
//	public final static int HEIGHT = 0;
//	public final static int WEIGHT = 1;
//	public final static int FOREARM = 2;
//	public final static int UPPER_ARM = 3;
//	public final static int TORSO = 4;
//	public final static int THIGH = 5;
//	public final static int SHIN = 6;

	public static enum Part{
		HEIGHT,
		WEIGHT,
		FOREARM,
		UPPER_ARM,
		TORSO,
		THIGH,
		SHIN 
	}
	
	int id;
	ArrayList<Workout> workouts;
	ArrayList<Integer> maxes;
	boolean isMale;
	ArrayList<Integer> measurements;
	
	public Athlete() {
		id = 0;
		workouts = new ArrayList<Workout>();
		maxes = new ArrayList<Integer>();
		isMale = true;
		measurements = new ArrayList<Integer>();
	}
	
	public Athlete(int id, ArrayList<Workout> workouts, ArrayList<Integer> maxes, 
			boolean isMale, ArrayList<Integer> measurements) {
		this.id = id;
		this.workouts = workouts;
		this.maxes = maxes;
		this.isMale = isMale;
		this.measurements = measurements;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Workout> getWorkouts() {
		return workouts;
	}

	public void setWorkouts(ArrayList<Workout> workouts) {
		this.workouts = workouts;
	}

	public ArrayList<Integer> getMaxes() {
		return maxes;
	}

	public void setMaxes(ArrayList<Integer> maxes) {
		this.maxes = maxes;
	}
	
	public Athlete clone(){
		try {
			return (Athlete) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String toString(){
		return ""+id;
	}

	public boolean isMale() {
		return isMale;
	}

	public void setMale(boolean isMale) {
		this.isMale = isMale;
	}

	public ArrayList<Integer> getMeasurements() {
		return measurements;
	}

	public void setMeasurements(ArrayList<Integer> measurements) {
		this.measurements = measurements;
	}
	
}
