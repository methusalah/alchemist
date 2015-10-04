package model.ES.component.motion.physic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class PhysicForce implements EntityComponent {
	public final double range;
	public final double radius;
	public final double force;
	public final List<String> exceptions;

	
	public PhysicForce(@JsonProperty("range")double range,
			@JsonProperty("radius")double radius,
			@JsonProperty("force")double force,
			@JsonProperty("exceptions")String... exceptions) {
		this.range = range;
		this.radius = radius;
		this.force = force;
		this.exceptions = new ArrayList<>(Arrays.asList(exceptions));
	}
}
