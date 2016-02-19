package processor.logic.interaction;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import javafx.geometry.Point3D;
import model.ECS.pipeline.Processor;

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
