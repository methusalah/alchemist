package model.ES.processor.motion;

import model.ES.component.motion.PlanarPosition;
import model.ES.component.motion.PlanarPossibleMotion;
import model.ES.component.motion.PlayerOrder;
import util.LogUtil;
import util.entity.CompMask;
import util.entity.Entity;
import util.entity.EntityGroup;
import util.entity.EntityPool;
import util.entity.Processor;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;

public class RealisticMotionProc extends Processor {

	public RealisticMotionProc() {
		mask = new CompMask(PlanarPosition.class, PlanarPossibleMotion.class);
	}

	@Override
	protected void onUpdate(float elapsedTime) {
		for(Entity e : entities.getCopyList()){
			applyMotion(e);
		}
	}

	private void applyMotion(Entity e) {
		PlanarPossibleMotion motion = e.get(PlanarPossibleMotion.class);
		PlanarPosition position = e.get(PlanarPosition.class);
		
		double newOrientation = position.getOrientation() + motion.getRotation();
		Point2D newPosition = position.getPosition().getTranslation(newOrientation, motion.getDistance());
		
		if(false){
//		if(!Map.getCollision(newPosition).isEmpty()){
//			changeNewPosition
		} else {
			e.remove(PlanarPossibleMotion.class);
			e.addOrUpdate(new PlanarPosition(newPosition, newOrientation));
			LogUtil.info("new position " + newPosition + " orientation : "+AngleUtil.toDegrees(newOrientation));
		}
	}
}
