package com.mikelady.smartbell.primitives;

import java.util.ArrayList;

public class LiftingSet {

	public static enum ExerciseId{
		PRESS(0),
		BENCH(1),
		BACK_SQUAT(2);
		private int id;
		private ExerciseId(int id){
			this.id = id;
		}
		public int getId() {
			return id;
		}
	}
	
	int id;
	long timestamp;
	int exercise;
	ArrayList<Moment> moments;
	int weightLifted;
	int reps;
	
	public LiftingSet() {
		exercise = ExerciseId.BACK_SQUAT.getId();
		moments = new ArrayList<Moment>();
		weightLifted = 135;
		reps = 0;
	}
	
	public LiftingSet(int exercise, ArrayList<Moment> moments, int weightLifted, int reps) {
		this.exercise = exercise;
		this.moments = moments;
		this.weightLifted = weightLifted;
		this.reps = reps;
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

	public ArrayList<Moment> getMoments() {
		return moments;
	}

	public void setMoments(ArrayList<Moment> moments) {
		this.moments = moments;
	}

	public int getWeightLifted() {
		return weightLifted;
	}

	public void setWeightLifted(int weightLifted) {
		this.weightLifted = weightLifted;
	}

	public int getReps() {
		return reps;
	}

	public void setReps(int reps) {
		this.reps = reps;
	}

}
