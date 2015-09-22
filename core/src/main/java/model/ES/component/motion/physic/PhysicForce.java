package model.ES.component.motion.physic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.simsilica.es.EntityComponent;

public class PhysicForce implements EntityComponent {
	public final double range;
	public final double radius;
	public final double force;
	public final List<String> exceptions;

	
	public PhysicForce(double range, double radius, double force, String... exceptions) {
		this.range = range;
		this.radius = radius;
		this.force = force;
		this.exceptions = new ArrayList<>(Arrays.asList(exceptions));
	}
}
