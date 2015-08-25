package model.ES.processor;

import model.ModelManager;
import model.ES.component.motion.PlanarIntendedMotion;
import model.ES.component.motion.PlanarPosition;
import model.ES.component.motion.PlayerOrder;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;

import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;

import controller.entityAppState.Processor;

public class OrderProc extends Processor {
	
	@Override
	protected void registerSets() {
		register(PlanarPosition.class);
	}
	
	@Override
	protected void onUpdated(float elapsedTime) {
		if(ModelManager.command.target == null)
			return;
		
        for(EntitySet set : sets)
        	for (Entity e : set){
        		LogUtil.info("command obeyed "+e.getId());
        		PlanarPosition pos = e.get(PlanarPosition.class);
        		
        		double angleToTarget = ModelManager.command.target.getSubtraction(pos.getPosition()).getAngle();
        		
        		// rotation
        		double rotation = 0;
        		Point2D front = pos.getPosition().getTranslation(pos.getOrientation(), 1);
        		int turn = AngleUtil.getTurn(pos.getPosition(), front, ModelManager.command.target);
        		if(turn != AngleUtil.NONE || angleToTarget != pos.getOrientation()){
        			double diff = AngleUtil.getSmallestDifference(pos.getOrientation(), angleToTarget);
        			if(turn >= 0)
        				rotation = diff;
        			else
        				rotation = -diff;
        		}

        		// distance
        		double distance = 0;
        		if(ModelManager.command.thrust)
        			distance = Double.POSITIVE_INFINITY;
        		
        		PlanarIntendedMotion desiredMotion = new PlanarIntendedMotion(distance, rotation);
        		LogUtil.info(desiredMotion.toString());
        		setComp(e, desiredMotion);
//        		removeComp(e, PlayerOrder.class);
        	}
	}

}
