package com.example.smartbell.primitives;

import java.util.ArrayList;

public class Athlete {

	ArrayList<Workout> workouts;
	Body body;
	
	public Athlete() {
		workouts = new ArrayList<Workout>();
		body = new Body();
	}
	
	public Athlete(ArrayList<Workout> workouts, Body body) {
		this.workouts = workouts;
		this.body = body;
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

}
