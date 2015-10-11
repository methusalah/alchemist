package model.ES.richData;

import java.text.DecimalFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Angle {

	private final double value;
	
	public Angle(@JsonProperty("value")double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}
	
	DecimalFormat df = new DecimalFormat("0.##");
	@Override
	public String toString() {
		return getClass().getSimpleName() + "("+df.format(value)+")";
	}
	
}
