package model.ES.richData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Angle {

	private final double value;
	
	public Angle(@JsonProperty("value")double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}
	
}
