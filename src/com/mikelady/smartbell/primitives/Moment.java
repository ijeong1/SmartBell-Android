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
	
	Float[] correctedGyro = new Float[3];
	Float[] correctedAcc = new Float[3];
	Float[] correctedCompass = new Float[3];
	
	Float[] rawGyro = new Float[3];
	Float[] rawAcc = new Float[3];
	Float[] rawCompass = new Float[3];
	
	public Moment(Long timestamp, Float[] quat, Float[] linacc, Float[] corrected, Float[] raw) {
		this.timestamp = timestamp;
		this.quat = quat;
		this.linAcc = linacc;
		
		correctedGyro[0] = corrected[0];
		correctedGyro[1] = corrected[1];
		correctedGyro[2] = corrected[2];
		correctedAcc[0] = corrected[3];
		correctedAcc[1] = corrected[4];
		correctedAcc[2] = corrected[5];
		correctedCompass[0] = corrected[6];
		correctedCompass[1] = corrected[7];
		correctedCompass[2] = corrected[8];
		
		rawGyro[0] = raw[0];
		rawGyro[1] = raw[1];
		rawGyro[2] = raw[2];
		rawAcc[0] = raw[3];
		rawAcc[1] = raw[4];
		rawAcc[2] = raw[5];
		rawCompass[0] = raw[6];
		rawCompass[1] = raw[7];
		rawCompass[2] = raw[8];
	}
	
	public String toString(){
		String s = "";
		
		s = "Moment "+timestamp+" quat[X] "+this.quat[X]+" quat[Y] "+this.quat[Y]+" quat[Z] "+this.quat[Z]+"\n";
		s += " linAcc[X] "+this.linAcc[X]+" linAcc[Y] "+this.linAcc[Y]+" linacc[Z] "+this.linAcc[Z];
		s += " correctedGyro[X] "+this.correctedGyro[X]+" correctedGyro[Y] "+this.correctedGyro[Y]+" correctedGyro[Z] "+this.correctedGyro[Z];
		s += " correctedAcc[X] "+this.correctedAcc[X]+" correctedAcc[Y] "+this.correctedAcc[Y]+" correctedAcc[Z] "+this.correctedAcc[Z];
		s += " correctedCompass[X] "+this.correctedCompass[X]+" correctedCompass[Y] "+this.correctedCompass[Y]+" correctedCompass[Z] "+this.correctedCompass[Z];
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

	public Float[] getCorrectedGyro() {
		return correctedGyro;
	}

	public void setCorrectedGyro(Float[] correctedGyro) {
		this.correctedGyro = correctedGyro;
	}

	public Float[] getCorrectedAcc() {
		return correctedAcc;
	}

	public void setCorrectedAcc(Float[] correctedAcc) {
		this.correctedAcc = correctedAcc;
	}

	public Float[] getCorrectedCompass() {
		return correctedCompass;
	}

	public void setCorrectedCompass(Float[] correctedCompass) {
		this.correctedCompass = correctedCompass;
	}

	public Float[] getRawGyro() {
		return rawGyro;
	}

	public void setRawGyro(Float[] rawGyro) {
		this.rawGyro = rawGyro;
	}

	public Float[] getRawAcc() {
		return rawAcc;
	}

	public void setRawAcc(Float[] rawAcc) {
		this.rawAcc = rawAcc;
	}

	public Float[] getRawCompass() {
		return rawCompass;
	}

	public void setRawCompass(Float[] rawCompass) {
		this.rawCompass = rawCompass;
	}


}
