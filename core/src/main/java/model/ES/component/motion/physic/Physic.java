package model.ES.component.motion.physic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

import model.ES.richData.PhysicStat;
import util.geometry.geom2d.Point2D;

public class Physic implements EntityComponent {
	public final Point2D velocity;
	public final PhysicStat stat;
	public final EntityId spawnerException;
	
	public Physic(@JsonProperty("velocity")Point2D velocity,
			@JsonProperty("physicStat")PhysicStat physicStat,
			@JsonProperty("spawnerException")EntityId spawnerException) {
		this.velocity = velocity;
		this.stat = physicStat;
		this.spawnerException = spawnerException;
	}
}
