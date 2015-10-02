package model.ES.processor.command;

import model.ModelManager;
import model.ES.component.command.PlanarNeededRotation;
import model.ES.component.command.PlanarNeededThrust;
import model.ES.component.command.PlayerControl;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.PlanarVelocityToApply;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;

import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;

import controller.entityAppState.Processor;

public class PlayerOrthogonalThrustControlProc extends Processor {
	
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
        		PlanarStance stance = e.get(PlanarStance.class);
        		if(!ModelManager.command.thrust.isOrigin()
        				&& stance.coord.getDistance(ModelManager.command.target) > 0.1){
            		PlanarNeededThrust thrust = new PlanarNeededThrust(ModelManager.command.thrust.getRotation(AngleUtil.RIGHT));
            		setComp(e, thrust);
        		}
        	}
	}
}
