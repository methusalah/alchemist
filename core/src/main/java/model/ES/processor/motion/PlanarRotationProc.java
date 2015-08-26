package model.ES.processor.motion;

import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;
import model.ModelManager;
import model.ES.component.motion.PlanarNeededRotation;
import model.ES.component.motion.PlanarThrust;
import model.ES.component.motion.PlanarInertia;
import model.ES.component.motion.PlanarMotionCapacity;
import model.ES.component.motion.PlanarPosition;
import model.ES.component.motion.PlanarMotion;
import model.ES.component.motion.PlayerOrder;
import model.battlefield.army.motion.Motion;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class PlanarRotationProc extends Processor {
	
	@Override
	protected void registerSets() {
		register(PlanarNeededRotation.class, PlanarMotionCapacity.class, PlanarPosition.class);
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
		PlanarPosition position = e.get(PlanarPosition.class); 
		
		double maxRotation = capacity.getMaxRotationSpeed() * elapsedTime;
		maxRotation = Math.min(Math.abs(neededRotation.getRotation()), maxRotation);
		double possibleRotation = maxRotation*Math.signum(neededRotation.getRotation());
		
		PlanarPosition newPosition = new PlanarPosition(position.getPosition(), position.getOrientation() + possibleRotation);
		
		setComp(e, newPosition);
		removeComp(e, PlanarNeededRotation.class);
		
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + System.lineSeparator());
		sb.append("    needed rotation : "+ AngleUtil.toDegrees(neededRotation.getRotation()) + System.lineSeparator());
		sb.append("    rotation capacity : " + AngleUtil.toDegrees(possibleRotation) + " (rotation speed : "+ AngleUtil.toDegrees(capacity.getMaxRotationSpeed()) + ")" + System.lineSeparator());
		sb.append("    new orientation : " + AngleUtil.toDegrees(newPosition.getOrientation()) + System.lineSeparator());
		app.getDebugger().add(sb.toString());
	}
}
