package com.example.smartbell.primitives;

import java.util.ArrayList;

public class Workout {

	Long timeStarted;
	ArrayList<LiftingSet> sets;
	
	public Workout() {
		timeStarted = System.currentTimeMillis();
		sets = new ArrayList<LiftingSet>();
	}

	public Workout(Long timeStarted, ArrayList<LiftingSet> sets) {
		this.timeStarted = timeStarted;
		this.sets = sets;
	}

	public Long getTimeStarted() {
		return timeStarted;
	}

	public void setTimeStarted(Long timeStarted) {
		this.timeStarted = timeStarted;
	}

	public ArrayList<LiftingSet> getSets() {
		return sets;
	}

	public void setSets(ArrayList<LiftingSet> sets) {
		this.sets = sets;
	}
}
