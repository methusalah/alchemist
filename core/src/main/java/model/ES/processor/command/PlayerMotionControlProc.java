package model.ES.processor.command;

import model.ModelManager;
import model.ES.component.planarMotion.PlanarNeededRotation;
import model.ES.component.planarMotion.PlanarStance;
import model.ES.component.planarMotion.PlanarNeededVelocity;
import model.ES.component.planarMotion.PlayerControl;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;

import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;

import controller.entityAppState.Processor;

public class PlayerMotionControlProc extends Processor {
	
	@Override
	protected void registerSets() {
		register(PlanarStance.class, PlayerControl.class);
	}
	
	@Override
	protected void onUpdated(float elapsedTime) {
		if(ModelManager.command.target == null)
			return;
		
        for(EntitySet set : sets)
        	for (Entity e : set){
        		PlanarNeededRotation nr = addNeededRotation(e);
        		PlanarNeededVelocity t = addThrust(e);
        		
        		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + System.lineSeparator());
        		sb.append("    target : "+ ModelManager.command.target + "/thrust = "+ ModelManager.command.thrust + System.lineSeparator());
        		sb.append("    needed rotation angle : " + nr + System.lineSeparator());
        		sb.append("    thrust : " + (t!=null) + System.lineSeparator());
        		app.getDebugger().add(sb.toString());
        	}
	}
	
	private PlanarNeededRotation addNeededRotation(Entity e){
		PlanarStance stance = e.get(PlanarStance.class);
		double angleToTarget = ModelManager.command.target.getSubtraction(stance.getCoord()).getAngle();

		// rotation
		double neededRotAngle = 0;
		Point2D front = stance.getCoord().getTranslation(stance.getOrientation(), 1);
		int turn = AngleUtil.getTurn(stance.getCoord(), front, ModelManager.command.target);
		if(turn != AngleUtil.NONE){// || angleToTarget != stance.getOrientation()){
			double diff = AngleUtil.getSmallestDifference(stance.getOrientation(), angleToTarget);
			if(turn >= 0)
				neededRotAngle = diff;
			else
				neededRotAngle = -diff;
		}
		
		if(Math.abs(neededRotAngle) > 0.01){
			PlanarNeededRotation neededRotation = new PlanarNeededRotation(neededRotAngle);
    		setComp(e, neededRotation);
    		return neededRotation;
		}
		return null;
	}
	
	private PlanarNeededVelocity addThrust(Entity e){
		PlanarStance stance = e.get(PlanarStance.class);
		if(!ModelManager.command.thrust.isOrigin()
				&& stance.getCoord().getDistance(ModelManager.command.target) > 0.1){
    		PlanarNeededVelocity thrust = new PlanarNeededVelocity(ModelManager.command.thrust);
    		setComp(e, thrust);
    		return thrust;
		}
		return null;
	}

}
