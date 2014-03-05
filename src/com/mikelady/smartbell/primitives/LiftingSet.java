package com.mikelady.smartbell.primitives;

import java.util.ArrayList;

public class LiftingSet {

	String exercise;
	ArrayList<Moment> moments;
	int weightLifted;
	int reps;
	
	public LiftingSet() {
		exercise = "Squat";
		moments = new ArrayList<Moment>();
		weightLifted = 135;
		reps = 0;
	}
	
	public LiftingSet(String exercise, ArrayList<Moment> moments, int weightLifted, int reps) {
		this.exercise = exercise;
		this.moments = moments;
		this.weightLifted = weightLifted;
		this.reps = reps;
	}

	public String getExercise() {
		return exercise;
	}

	public void setExercise(String exercise) {
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
