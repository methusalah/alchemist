package model.ES.processor;

import model.ES.component.motion.PlanarDesiredMotion;
import model.ES.component.motion.PlanarPosition;
import model.ES.component.motion.PlayerOrder;
import util.math.AngleUtil;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class OrderProc extends Processor {
	
	@Override
	protected void registerSets() {
		register(entityData.getEntities(PlanarPosition.class, PlayerOrder.class));
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime){
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
		setComp(e, desiredMotion);
		removeComp(e, PlayerOrder.class);
	}

}
