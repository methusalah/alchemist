package model.ES.component.motion.physic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

import model.ES.richData.CollisionShape;
import model.ES.richData.PhysicStat;
import util.geometry.geom2d.Point2D;
import util.math.Fraction;

public class Physic implements EntityComponent {
	public final Point2D velocity;
	public final PhysicStat stat;
	public final EntityId spawnerException;
	
	public Physic() {
		velocity = Point2D.ORIGIN;
		stat = new PhysicStat("", 0, new CollisionShape(0), new Fraction(0));
		spawnerException = null;
	}

	public Physic(@JsonProperty("velocity")Point2D velocity,
			@JsonProperty("physicStat")PhysicStat physicStat,
			@JsonProperty("spawnerException")EntityId spawnerException) {
		this.velocity = velocity;
		this.stat = physicStat;
		this.spawnerException = spawnerException;
	}

	public Point2D getVelocity() {
		return velocity;
	}

	public PhysicStat getStat() {
		return stat;
	}

	public EntityId getSpawnerException() {
		return spawnerException;
	}
}
