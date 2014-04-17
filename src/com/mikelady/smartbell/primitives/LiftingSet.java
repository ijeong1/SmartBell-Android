package com.mikelady.smartbell.primitives;

import java.util.ArrayList;

import android.util.Log;

public class LiftingSet {

	public static enum ExerciseId{
		PRESS(0),
		BENCH(1),
		SQUAT(2);
		private int id;
		private ExerciseId(int id){
			this.id = id;
		}
		public int getId() {
			return id;
		}
		public static int getId(String name){
			Log.d("LiftingSet", "name: "+name);
			return valueOf(name.toUpperCase()).ordinal();
		}
	}
	
	int id;
	long timestamp;
	int exercise;
//	ArrayList<Moment> moments;
	ArrayList<Rep> reps;
	int weightLifted;
	int num_reps;
	
	public LiftingSet() {
		exercise = ExerciseId.SQUAT.getId();
//		moments = new ArrayList<Moment>();
		reps = new ArrayList<Rep>();
		weightLifted = 135;
		num_reps = 0;
	}
	
	public LiftingSet(int exercise, ArrayList<Rep> reps, int weightLifted, int num_reps) { //ArrayList<Moment> moments
		this.exercise = exercise;
//		this.moments = moments;
		this.reps = reps;
		this.weightLifted = weightLifted;
		this.num_reps = num_reps;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getExercise() {
		return exercise;
	}

	public void setExercise(int exercise) {
		this.exercise = exercise;
	}

/*	public ArrayList<Moment> getMoments() {
		return moments;
	}

	public void setMoments(ArrayList<Moment> moments) {
		this.moments = moments;
	}*/

	public int getWeightLifted() {
		return weightLifted;
	}

	public void setWeightLifted(int weightLifted) {
		this.weightLifted = weightLifted;
	}

	public int getReps() {
		return num_reps;
	}

	public void setReps(int reps) {
		this.num_reps = reps;
	}

}
