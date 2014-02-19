package com.mikelady.smartbell.primitives;

import java.util.ArrayList;

public class Athlete {
	public final static int MAX_PRESS = 0;
	public final static int MAX_BENCH = 1;
	public final static int MAX_BACK_SQUAT = 1;

	int id;
	ArrayList<Workout> workouts;
	Body body;
	ArrayList<Integer> maxes;
	
	public Athlete() {
		id = 0;
		workouts = new ArrayList<Workout>();
		body = new Body();
		maxes = new ArrayList<Integer>();
	}
	
	public Athlete(int id, ArrayList<Workout> workouts, Body body, ArrayList<Integer> maxes) {
		this.id = id;
		this.workouts = workouts;
		this.body = body;
		this.maxes = maxes;
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

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public ArrayList<Integer> getMaxes() {
		return maxes;
	}

	public void setMaxes(ArrayList<Integer> maxes) {
		this.maxes = maxes;
	}

}
