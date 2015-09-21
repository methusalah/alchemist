package model.ES.component.motion.physic;

import util.geometry.geom2d.Point2D;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

import model.ES.richData.CollisionShape;
import model.ES.richData.PhysicStat;

public class Physic implements EntityComponent {
	public final Point2D velocity;
	public final PhysicStat stat;
	public final EntityId spawnerException;
	
	public Physic(Point2D velocity, PhysicStat physicStat, EntityId spawnerException) {
		this.velocity = velocity;
		this.stat = physicStat;
		this.spawnerException = spawnerException;
	}
}
