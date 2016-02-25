package logic.processor.logic.command;

import com.brainless.alchemist.model.ECS.pipeline.Pipeline;
import com.brainless.alchemist.model.ECS.pipeline.Processor;
import com.simsilica.es.Entity;

import component.motion.MotionCapacity;
import component.motion.PlanarNeededRotation;
import component.motion.PlanarStance;
import util.math.Angle;

public class NeededRotationProc extends Processor {
	
	@Override
	protected void registerSets() {
		registerDefault(PlanarNeededRotation.class, MotionCapacity.class, PlanarStance.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		PlanarNeededRotation neededRotation = e.get(PlanarNeededRotation.class);
		MotionCapacity capacity = e.get(MotionCapacity.class);
		PlanarStance stance = e.get(PlanarStance.class); 
		
		double maxRotation = capacity.maxRotationSpeed * Pipeline.getSecondPerTick();
		maxRotation = Math.min(Math.abs(neededRotation.angle.getValue()), maxRotation);
		double possibleRotation = maxRotation*Math.signum(neededRotation.angle.getValue());
		
		PlanarStance newStance = new PlanarStance(stance.coord, new Angle(stance.orientation.getValue() + possibleRotation), stance.elevation, stance.upVector);
		
		setComp(e, newStance);
		removeComp(e, PlanarNeededRotation.class);
	}
}
