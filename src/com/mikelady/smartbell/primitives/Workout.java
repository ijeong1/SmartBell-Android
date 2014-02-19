package com.mikelady.smartbell.primitives;

import java.util.ArrayList;

public class Workout {

	Long timeStamp;
	ArrayList<LiftingSet> sets;
	
	public Workout() {
		timeStamp = System.currentTimeMillis();
		sets = new ArrayList<LiftingSet>();
	}

	public Workout(Long timeStarted, ArrayList<LiftingSet> sets) {
		this.timeStamp = timeStarted;
		this.sets = sets;
	}

	public Long getTimeStarted() {
		return timeStamp;
	}

	public void setTimeStarted(Long timeStarted) {
		this.timeStamp = timeStarted;
	}

	public ArrayList<LiftingSet> getSets() {
		return sets;
	}

	public void setSets(ArrayList<LiftingSet> sets) {
		this.sets = sets;
	}
}
