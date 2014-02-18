package com.example.smartbell.primitives;
import java.util.ArrayList;
import java.util.HashMap;


public class Body {
	public final static int HEIGHT = 0;
	public final static int WEIGHT = 1;
	public final static int FOREARM = 2;
	public final static int UPPER_ARM = 3;
	public final static int TORSO = 4;
	public final static int THIGH = 5;
	public final static int SHIN = 6;
	
	Integer athleteID;
	boolean isMale;
	ArrayList<Integer> measurements;
	
	
	public Body(){
		athleteID = 0;
		isMale = true;
		measurements = new ArrayList<Integer>();
	}
	
	public Body(Integer athleteID, boolean isMale, ArrayList<Integer> measurements){
		this.athleteID = athleteID;
		this.isMale = isMale;
		this.measurements = measurements;
	}

}
