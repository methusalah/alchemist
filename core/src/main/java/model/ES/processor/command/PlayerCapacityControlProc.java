package model.ES.processor.command;

import model.ModelManager;
import model.ES.component.command.PlanarNeededRotation;
import model.ES.component.command.PlayerControl;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.PlanarVelocityToApply;
import model.ES.component.shipGear.CapacityTrigger;
import model.ES.component.shipGear.Gun;

import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;

import controller.entityAppState.Processor;

public class PlayerCapacityControlProc extends Processor {

	@Override
	protected void registerSets() {
		register(CapacityTrigger.class, PlayerControl.class);
	}
	
	@Override
	protected void onUpdated(float elapsedTime) {
        for(EntitySet set : sets)
        	for (Entity e : set){
        		CapacityTrigger activation = e.get(CapacityTrigger.class);
        		boolean activated = ModelManager.command.capacities.contains(activation.getFlag());
        		setComp(e, new CapacityTrigger(activation.getFlag(), activated));
        	}
	}
}
