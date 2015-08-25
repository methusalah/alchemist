package model.ES.processor.motion;

import model.ES.component.motion.PlanarInertia;
import model.ES.component.motion.PlanarPosition;
import model.ES.component.motion.PlanarMotion;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class RealisticMotionProc extends Processor {
	
	@Override
	protected void registerSets() {
		register(PlanarPosition.class, PlanarMotion.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime){
		PlanarMotion motion = e.get(PlanarMotion.class);
		PlanarPosition position = e.get(PlanarPosition.class);

		LogUtil.info("motion application");
		
		double newOrientation = position.getOrientation() + motion.getRotation();
		Point2D newPosition = position.getPosition().getTranslation(newOrientation, motion.getDistance());
		
		setComp(e, new PlanarPosition(newPosition, newOrientation));
		removeComp(e, PlanarMotion.class);
	}
}
