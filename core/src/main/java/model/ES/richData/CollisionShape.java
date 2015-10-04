package model.ES.richData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CollisionShape {
	public final double radius;
	
	public CollisionShape(@JsonProperty("radius")double radius) {
		this.radius = radius;
	}
}
