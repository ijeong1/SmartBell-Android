package com.example.smartbell.primitives;

import java.util.ArrayList;

public class LiftingSet {

	String exercise;
	ArrayList<Moment> moments;
	int weightLifted;
	
	public LiftingSet() {
		exercise = "Squat";
		moments = new ArrayList<Moment>();
		weightLifted = 135;
	}
	
	public LiftingSet(String exercise, ArrayList<Moment> moments, int weightLifted) {
		this.exercise = exercise;
		this.moments = moments;
		this.weightLifted = weightLifted;
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

}
