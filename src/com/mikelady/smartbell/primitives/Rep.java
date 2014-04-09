package com.mikelady.smartbell.primitives;

import java.util.ArrayList;

public class Rep {

	public static enum CategoryId{
		GOOD(0),

		;
		private int id;
		private CategoryId(int id){
			this.id = id;
		}
		public int getId() {
			return id;
		}
	}
	
	int id;
	long timestamp;
	int category;
	ArrayList<Moment> moments;
	
	public Rep() {
		category = CategoryId.GOOD.getId();
		moments = new ArrayList<Moment>();

	}
	
	public Rep(int exercise, ArrayList<Moment> moments, int weightLifted, int reps) {
		this.category = exercise;
		this.moments = moments;
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

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public ArrayList<Moment> getMoments() {
		return moments;
	}

	public void setMoments(ArrayList<Moment> moments) {
		this.moments = moments;
	}

}
