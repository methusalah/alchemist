package model.ES.richData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import util.math.Fraction;

public class PhysicStat {
	public final String type;
	public final List<String> exceptions;
	public final CollisionShape shape;
	public final Fraction restitution;
	public final double mass;
	
	public PhysicStat(@JsonProperty("type")String type,
			@JsonProperty("mass")double mass,
			@JsonProperty("shape")CollisionShape shape,
			@JsonProperty("restitution")Fraction restitution,
			@JsonProperty("exceptions")String... exceptions) {
		this.type = type;
		this.mass = mass;
		this.shape = shape;
		this.restitution = restitution;
		this.exceptions = new ArrayList<>(Arrays.asList(exceptions));
	}
}
