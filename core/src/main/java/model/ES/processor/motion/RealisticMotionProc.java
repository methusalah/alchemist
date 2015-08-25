package model.ES.processor.motion;

import model.ES.component.motion.PlanarPosition;
import model.ES.component.motion.PlanarPossibleMotion;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class RealisticMotionProc extends Processor {
	
	@Override
	protected void registerSets() {
		register(PlanarPosition.class, PlanarPossibleMotion.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime){
		PlanarPossibleMotion motion = e.get(PlanarPossibleMotion.class);
		PlanarPosition position = e.get(PlanarPosition.class);
		
		double newOrientation = position.getOrientation() + motion.getRotation();
		Point2D newPosition = position.getPosition().getTranslation(newOrientation, motion.getDistance());
		
		if(false){
//		if(!Map.getCollision(newPosition).isEmpty()){
//			changeNewPosition
		} else {
			setComp(e, new PlanarPosition(newPosition, newOrientation));
			removeComp(e, PlanarPossibleMotion.class);
			LogUtil.info("new position " + newPosition + " orientation : "+AngleUtil.toDegrees(newOrientation));
		}
	}
}
