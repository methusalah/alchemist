package util.math;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Fraction {
	private final double value;

	public Fraction(@JsonProperty("value")double value) {
		this.value = value;
	}
	public double getValue() {
		return value;
	}
}
