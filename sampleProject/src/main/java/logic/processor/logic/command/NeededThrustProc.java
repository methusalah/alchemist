package logic.processor.logic.command;

import org.dyn4j.dynamics.Body;

import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.simsilica.es.Entity;

import component.motion.MotionCapacity;
import component.motion.PlanarNeededThrust;
import component.motion.physic.Physic;
import logic.processor.Pool;
import logic.util.Vector2Adapter;
import util.LogUtil;
import util.geometry.geom2d.Point2D;

public class NeededThrustProc extends BaseProcessor {
	
	@Override
	protected void registerSets() {
		registerDefault(Physic.class, PlanarNeededThrust.class, MotionCapacity.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		PlanarNeededThrust thrust = e.get(PlanarNeededThrust.class);
		MotionCapacity capacity = e.get(MotionCapacity.class);

		Body b = Pool.bodies.get(e.getId());
		double orientation = b.getTransform().getRotation();

		// orient the velocity according to the origin to scale its X and Y components
		Point2D originalVel = thrust.getDirection().getRotation(-orientation).getNormalized();
		double velX = originalVel.x * (originalVel.x > 0? capacity.thrustPower : capacity.frontalThrustPower);
		double velY = originalVel.y * capacity.lateralThrustPower;
		
		Point2D velocity = new Point2D(velX, velY).getRotation(orientation);

		b.applyForce(new Vector2Adapter(velocity));
	}
}
