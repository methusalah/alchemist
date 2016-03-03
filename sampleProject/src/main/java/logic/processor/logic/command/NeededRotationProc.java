package logic.processor.logic.command;

import org.dyn4j.dynamics.Body;

import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.brainless.alchemist.model.ECS.pipeline.Pipeline;
import com.simsilica.es.Entity;

import component.motion.MotionCapacity;
import component.motion.PlanarNeededRotation;
import component.motion.physic.Physic;
import logic.processor.Pool;

public class NeededRotationProc extends BaseProcessor {
	
	@Override
	protected void registerSets() {
		registerDefault(PlanarNeededRotation.class, MotionCapacity.class, Physic.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		PlanarNeededRotation neededRotation = e.get(PlanarNeededRotation.class);
		MotionCapacity capacity = e.get(MotionCapacity.class);
		
		Body b = Pool.bodies.get(e.getId());

		
		double maxRotation = capacity.maxRotationSpeed * Pipeline.getSecondPerTick();
		maxRotation = Math.min(Math.abs(neededRotation.angle.getValue()), maxRotation);
		double possibleRotation = maxRotation*Math.signum(neededRotation.angle.getValue());

		b.getTransform().setRotation(b.getTransform().getRotation() + possibleRotation);
	}
}
