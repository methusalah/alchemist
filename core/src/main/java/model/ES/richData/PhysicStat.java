package model.ES.richData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PhysicStat {
	public final String type;
	public final List<String> exceptions;
	public final CollisionShape shape;
	public final double restitution;
	public final double mass;
	
	public PhysicStat(String type, double mass, CollisionShape shape, double restitution, String... exceptions) {
		this.type = type;
		this.mass = mass;
		this.shape = shape;
		this.restitution = restitution;
		this.exceptions = new ArrayList<>(Arrays.asList(exceptions));
	}
}
