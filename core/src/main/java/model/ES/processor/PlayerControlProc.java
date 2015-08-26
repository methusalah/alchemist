package model.ES.processor;

import model.ModelManager;
import model.ES.component.motion.PlanarNeededRotation;
import model.ES.component.motion.PlanarThrust;
import model.ES.component.motion.PlanarPosition;
import model.ES.component.motion.PlayerControl;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;

import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;

import controller.entityAppState.Processor;

public class PlayerControlProc extends Processor {
	
	@Override
	protected void registerSets() {
		register(PlanarPosition.class, PlayerControl.class);
	}
	
	@Override
	protected void onUpdated(float elapsedTime) {
		if(ModelManager.command.target == null)
			return;
		
        for(EntitySet set : sets)
        	for (Entity e : set){
        		PlanarNeededRotation nr = addNeededRotation(e);
        		PlanarThrust t = addThrust(e);
        		
        		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + System.lineSeparator());
        		sb.append("    target : "+ ModelManager.command.target + "/thrust = "+ ModelManager.command.thrust + System.lineSeparator());
        		sb.append("    needed rotation angle : " + nr + System.lineSeparator());
        		sb.append("    thrust : " + (t!=null) + System.lineSeparator());
        		app.getDebugger().add(sb.toString());
        	}
	}
	
	private PlanarNeededRotation addNeededRotation(Entity e){
		PlanarPosition pos = e.get(PlanarPosition.class);
		double angleToTarget = ModelManager.command.target.getSubtraction(pos.getPosition()).getAngle();

		// rotation
		double neededRotAngle = 0;
		Point2D front = pos.getPosition().getTranslation(pos.getOrientation(), 1);
		int turn = AngleUtil.getTurn(pos.getPosition(), front, ModelManager.command.target);
		if(turn != AngleUtil.NONE || angleToTarget != pos.getOrientation()){
			double diff = AngleUtil.getSmallestDifference(pos.getOrientation(), angleToTarget);
			if(turn >= 0)
				neededRotAngle = diff;
			else
				neededRotAngle = -diff;
		}
		
		if(neededRotAngle != 0){
			PlanarNeededRotation neededRotation = new PlanarNeededRotation(neededRotAngle);
    		setComp(e, neededRotation);
    		return neededRotation;
		}
		return null;
	}
	
	private PlanarThrust addThrust(Entity e){
		PlanarPosition pos = e.get(PlanarPosition.class);
		if(ModelManager.command.thrust
				&& pos.getPosition().getDistance(ModelManager.command.target) > 0.1){
    		PlanarThrust thrust = new PlanarThrust();
    		setComp(e, thrust);
    		return thrust;
		}
		return null;
	}

}
