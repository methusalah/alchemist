package model.ES.processor.motion;

import model.ES.component.motion.PlanarMotionCapacity;
import model.ES.component.motion.PlanarNeededRotation;
import model.ES.component.motion.PlanarStance;
import util.math.AngleUtil;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class PlanarRotationProc extends Processor {
	
	@Override
	protected void registerSets() {
		register(PlanarNeededRotation.class, PlanarMotionCapacity.class, PlanarStance.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime){
		manage(e, elapsedTime);
	}

	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime){
		manage(e, elapsedTime);
	}
	
	private void manage(Entity e, float elapsedTime){
		PlanarNeededRotation neededRotation = e.get(PlanarNeededRotation.class);
		PlanarMotionCapacity capacity = e.get(PlanarMotionCapacity.class);
		PlanarStance stance = e.get(PlanarStance.class); 
		
		double maxRotation = capacity.getMaxRotationSpeed() * elapsedTime;
		maxRotation = Math.min(Math.abs(neededRotation.getRotation()), maxRotation);
		double possibleRotation = maxRotation*Math.signum(neededRotation.getRotation());
		
		PlanarStance newStance = new PlanarStance(stance.getCoord(), stance.getOrientation() + possibleRotation, stance.getElevation(), stance.getUpVector());
		
		setComp(e, newStance);
		removeComp(e, PlanarNeededRotation.class);
		
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + System.lineSeparator());
		sb.append("    needed rotation : "+ AngleUtil.toDegrees(neededRotation.getRotation()) + System.lineSeparator());
		sb.append("    rotation capacity : " + AngleUtil.toDegrees(possibleRotation) + " (rotation speed : "+ AngleUtil.toDegrees(capacity.getMaxRotationSpeed()) + ")" + System.lineSeparator());
		sb.append("    new orientation : " + AngleUtil.toDegrees(newStance.getOrientation()) + System.lineSeparator());
		app.getDebugger().add(sb.toString());
	}
}
