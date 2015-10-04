package model.ES.processor.command;

import model.ES.component.command.PlanarNeededRotation;
import model.ES.component.motion.MotionCapacity;
import model.ES.component.motion.PlanarStance;
import util.math.AngleUtil;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class NeededRotationProc extends Processor {
	
	@Override
	protected void registerSets() {
		register(PlanarNeededRotation.class, MotionCapacity.class, PlanarStance.class);
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
		MotionCapacity capacity = e.get(MotionCapacity.class);
		PlanarStance stance = e.get(PlanarStance.class); 
		
		double maxRotation = capacity.maxRotationSpeed * elapsedTime;
		maxRotation = Math.min(Math.abs(neededRotation.angle), maxRotation);
		double possibleRotation = maxRotation*Math.signum(neededRotation.angle);
		
		PlanarStance newStance = new PlanarStance(stance.coord, stance.orientation + possibleRotation, stance.elevation, stance.upVector);
		
		setComp(e, newStance);
		removeComp(e, PlanarNeededRotation.class);
		
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + System.lineSeparator());
		sb.append("    needed rotation : "+ AngleUtil.toDegrees(neededRotation.angle) + System.lineSeparator());
		sb.append("    rotation capacity : " + AngleUtil.toDegrees(possibleRotation) + " (rotation speed : "+ AngleUtil.toDegrees(capacity.maxRotationSpeed) + ")" + System.lineSeparator());
		sb.append("    new orientation : " + AngleUtil.toDegrees(newStance.orientation) + System.lineSeparator());
		app.getDebugger().add(sb.toString());
	}
}
