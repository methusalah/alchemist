package model.ES.processor.interaction;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.entityAppState.Processor;
import model.ES.component.LifeTime;
import model.ES.component.interaction.ShockwaveOnTouch;
import model.ES.component.interaction.Touching;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.physic.PhysicForce;
import util.geometry.geom3d.Point3D;

public class ShockwaveOnTouchProc extends Processor{
	@Override
	protected void registerSets() {
		register(ShockwaveOnTouch.class, Touching.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime) {
		ShockwaveOnTouch shock = e.get(ShockwaveOnTouch.class);
		EntityId eid = entityData.createEntity();
		entityData.setComponent(eid, new PhysicForce(0, shock.getRadius(), shock.getForce()));
		entityData.setComponent(eid, new PlanarStance(e.get(Touching.class).getCoord(), 0, 0.5, Point3D.UNIT_Z));
		entityData.setComponent(eid, new LifeTime(System.currentTimeMillis(), shock.getDuration()));
	}

}
