package com.mikelady.smartbell.primitives;

public class Moment {
	Long timestamp;
	Float[] euler;
	Float[] linAcc;

	
	public Moment(Long timestamp, Float[] euler, Float[] linacc) {
		this.timestamp = timestamp;
		this.euler = euler;
		this.linAcc = linacc;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Float[] getLinAcc() {
		return linAcc;
	}

	public void setLinAcc(Float[] linacc) {
		this.linAcc = linacc;
	}

}
