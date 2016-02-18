package model.ES.processor.interaction;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.ECS.Processor;
import model.ECS.Naming;
import model.ES.component.lifeCycle.LifeTime;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.ShockwaveOnTouch;
import model.ES.component.motion.Touching;
import model.ES.component.motion.physic.PhysicForce;
import util.geometry.geom3d.Point3D;
import util.math.Angle;

public class ShockwaveOnTouchProc extends Processor{
	@Override
	protected void registerSets() {
		registerDefault(ShockwaveOnTouch.class, Touching.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		ShockwaveOnTouch shock = e.get(ShockwaveOnTouch.class);
		EntityId eid = entityData.createEntity();
		entityData.setComponent(eid, new Naming("shockwave"));
		entityData.setComponent(eid, new PhysicForce(0, shock.getRadius(), shock.getForce(), "Missile", "debris"));
		entityData.setComponent(eid, new PlanarStance(e.get(Touching.class).getCoord(), new Angle(0), 0.5, Point3D.UNIT_Z));
		entityData.setComponent(eid, new LifeTime(shock.getDuration()));
	}

}
