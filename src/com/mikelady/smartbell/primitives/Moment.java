package com.mikelady.smartbell.primitives;


public class Moment {
	public static final boolean TEST = false;
	
	public static final int X = 0;
	public static final int Y = 1;
	public static final int Z = 2;
	

	public static final int qW = 0;
	public static final int qX = 1;
	public static final int qY = 2;
	public static final int qZ = 3;	
	
	Long timestamp;
	Float[] quat;
	Float[] linAcc;
	Float[] compass;
	
	public Moment(Long timestamp, Float[] quat, Float[] linacc, Float[] compass) {
		this.timestamp = timestamp;
		this.quat = quat;
		this.linAcc = linacc;
		this.compass = compass;
	}
	
	public String toString(){
		String s = "";
		
		s = "Moment "+timestamp+" quat[X] "+this.quat[X]+" quat[Y] "+this.quat[Y]+" quat[Z] "+this.quat[Z]+"\n";
		s += " linAcc[X] "+this.linAcc[X]+" linAcc[Y] "+this.linAcc[Y]+" linacc[Z] "+this.linAcc[Z];
		s += " compass[X] "+this.compass[X]+" compass[Y] "+this.compass[Y]+" compass[Z] "+this.compass[Z];
		return s;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Float[] getQuat() {
		return quat;
	}

	public void setQuat(Float[] quat) {
		this.quat = quat;
	}

	public Float[] getLinAcc() {
		return linAcc;
	}

	public void setLinAcc(Float[] linacc) {
		this.linAcc = linacc;
	}

	public Float[] getCompass() {
		return compass;
	}

	public void setCompass(Float[] compass) {
		this.compass = compass;
	}

}
