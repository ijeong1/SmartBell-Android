package com.mikelady.smartbell.primitives;

import java.util.ArrayList;

public class Workout {
	int id;
	Long timestamp;
	int athleteId;
	ArrayList<LiftingSet> sets;
	
	public Workout() {
		timestamp = System.currentTimeMillis();
		sets = new ArrayList<LiftingSet>();
	}

	public Workout(Long timeStarted, ArrayList<LiftingSet> sets) {
		this.timestamp = timeStarted;
		this.sets = sets;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public int getAthleteId() {
		return athleteId;
	}

	public void setAthleteId(int athleteId) {
		this.athleteId = athleteId;
	}

	public ArrayList<LiftingSet> getSets() {
		return sets;
	}

	public void setSets(ArrayList<LiftingSet> sets) {
		this.sets = sets;
	}
	
	public String toString(){
		return ""+id;
	}
}
