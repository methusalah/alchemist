package component.motion.physic;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

import util.geometry.geom2d.Point2D;
import util.math.Fraction;

public class Physic implements EntityComponent {
	private final Point2D velocity;
	private final double angularVelocity;
	private final String type;
	private final List<String> exceptions;
	private final Fraction restitution;
	private final double mass;
	private final EntityId spawnerException;

	public Physic() {
		velocity = Point2D.ORIGIN;
		angularVelocity = 0;
		type = "";
		exceptions = new ArrayList<>();
		restitution = new Fraction(0);
		mass = 0;
		spawnerException = null;
	}

	public Physic(@JsonProperty("velocity") Point2D velocity,
			@JsonProperty("angularVelocity") double angularVelocity,
			@JsonProperty("type") String type,
			@JsonProperty("exceptions") List<String> exceptions,
			@JsonProperty("mass") double mass,
			@JsonProperty("restitution") Fraction restitution,
			@JsonProperty("spawnerException") EntityId spawnerException) {
		this.velocity = velocity;
		this.angularVelocity = angularVelocity;
		this.type = type;
		this.exceptions = exceptions;
		this.restitution = restitution;
		this.mass = mass;
		this.spawnerException = spawnerException;
	}

	public Point2D getVelocity() {
		return velocity;
	}

	public String getType() {
		return type;
	}

	public List<String> getExceptions() {
		return exceptions;
	}

	public Fraction getRestitution() {
		return restitution;
	}

	public double getMass() {
		return mass;
	}

	public EntityId getSpawnerException() {
		return spawnerException;
	}

	public double getAngularVelocity() {
		return angularVelocity;
	}
}
