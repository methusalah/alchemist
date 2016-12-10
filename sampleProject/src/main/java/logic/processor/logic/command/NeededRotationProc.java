package logic.processor.logic.command;

import org.dyn4j.dynamics.Body;

import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.brainless.alchemist.model.ECS.pipeline.Pipeline;
import com.simsilica.es.Entity;

import component.motion.MotionCapacity;
import component.motion.PlanarNeededRotation;
import component.motion.PlanarStance;
import component.motion.physic.Physic;
import logic.processor.Pool;
import util.math.Angle;

public class NeededRotationProc extends BaseProcessor {
	
	@Override
	protected void registerSets() {
		registerDefault(PlanarNeededRotation.class, MotionCapacity.class, Physic.class, PlanarStance.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		PlanarNeededRotation neededRotation = e.get(PlanarNeededRotation.class);
		MotionCapacity capacity = e.get(MotionCapacity.class);
		

		
		double maxRotation = capacity.maxRotationSpeed * Pipeline.getSecondPerTick();
		maxRotation = Math.min(Math.abs(neededRotation.angle.getValue()), maxRotation);
		double possibleRotation = maxRotation*Math.signum(neededRotation.angle.getValue());
		
		// we apply the rotation to the planar stance of the entity at once, to avoid the two data to be inconsistent.
		// If we don't, the other thread who ask for the planarstance will get a data different from the one stored in the body.
		PlanarStance stance = e.get(PlanarStance.class);
		PlanarStance newStance = new PlanarStance(stance.coord, new Angle(stance.orientation.getValue() + possibleRotation), stance.elevation, stance.upVector);

		// we apply the new rotation to the physic body
		Body b = Pool.bodies.get(e.getId());
		if(b != null){
			b.getTransform().setRotation(stance.orientation.getValue() + possibleRotation);
			b.setAngularVelocity(0);
		}
		
		
		setComp(e, newStance);
		removeComp(e, PlanarNeededRotation.class);
	}
}
