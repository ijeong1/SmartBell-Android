package com.example.smartbell.primitives;

import java.util.ArrayList;

public class Athlete {
	public final static int MAX_PRESS = 0;
	public final static int MAX_BENCH = 1;
	public final static int MAX_BACK_SQUAT = 1;

	ArrayList<Workout> workouts;
	Body body;
	ArrayList<Integer> maxes;
	
	public Athlete() {
		workouts = new ArrayList<Workout>();
		body = new Body();
		maxes = new ArrayList<Integer>();
	}
	
	public Athlete(ArrayList<Workout> workouts, Body body, ArrayList<Integer> maxes) {
		this.workouts = workouts;
		this.body = body;
		this.maxes = maxes;
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
