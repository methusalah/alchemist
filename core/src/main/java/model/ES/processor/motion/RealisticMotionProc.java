package model.ES.processor.motion;

import model.ES.component.motion.PlanarMotion;
import model.ES.component.motion.PlanarPosition;
import util.geometry.geom2d.Point2D;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class RealisticMotionProc extends Processor {
	
	@Override
	protected void registerSets() {
		register(PlanarPosition.class, PlanarMotion.class);
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
		PlanarMotion motion = e.get(PlanarMotion.class);
		PlanarPosition position = e.get(PlanarPosition.class);

		app.getDebugger().add("motion application");
		
		double newOrientation = position.getOrientation() + motion.getRotation();
		Point2D newPosition = position.getPosition().getTranslation(newOrientation, motion.getDistance());
		
		setComp(e, new PlanarPosition(newPosition, newOrientation));
		removeComp(e, PlanarMotion.class);
	}
}
