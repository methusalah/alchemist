package model.ES.processor;

import model.ES.component.motion.PlanarDesiredMotion;
import model.ES.component.motion.PlanarPosition;
import model.ES.component.motion.PlayerOrder;
import model.ES.events.TheoricalMotionAttachedEvent;
import util.entity.CompMask;
import util.entity.Entity;
import util.entity.EntityGroup;
import util.entity.EntityPool;
import util.entity.Processor;
import util.event.EventManager;
import util.math.AngleUtil;

public class OrderProc extends Processor {
	
	public OrderProc() {
		mask = new CompMask(PlanarPosition.class, PlayerOrder.class);
	}
	
	@Override
	protected void onUpdate(float elapsedTime) {
		for(Entity e : entities.getCopyList()){
			createMotion(e, elapsedTime);
		}
	}
	
	private void createMotion(Entity e, float elapsedTime){
		PlanarPosition pos = e.get(PlanarPosition.class);
		PlayerOrder order = e.get(PlayerOrder.class);
		
		double angleToTarget = order.getTarget().getSubtraction(pos.getPosition()).getAngle();
		
		// rotation
		double rotation = 0;
		int turn = AngleUtil.getTurn(pos.getOrientation(), angleToTarget);
		if(turn != AngleUtil.NONE){
			double diff = AngleUtil.getSmallestDifference(pos.getOrientation(), angleToTarget);
			rotation = diff*turn;
		}
		
		if(!AngleUtil.areSimilar(angleToTarget, pos.getOrientation()))
			rotation = angleToTarget;
		

		// distance
		double distance = 0;
		if(order.isThrust())
			distance = Double.POSITIVE_INFINITY;
		
		PlanarDesiredMotion desiredMotion = new PlanarDesiredMotion(distance, rotation, elapsedTime);
		e.add(desiredMotion);
		e.remove(PlayerOrder.class);
	}
}
