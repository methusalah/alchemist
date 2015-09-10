package model.ES.processor.command;

import model.ModelManager;
import model.ES.component.planarMotion.PlanarNeededRotation;
import model.ES.component.planarMotion.PlanarStance;
import model.ES.component.planarMotion.PlanarThrust;
import model.ES.component.planarMotion.PlayerControl;
import model.ES.component.shipGear.CapacityActivation;
import model.ES.component.shipGear.Gun;

import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;

import controller.entityAppState.Processor;

public class PlayerCapacityControlProc extends Processor {

	@Override
	protected void registerSets() {
		register(CapacityActivation.class, PlayerControl.class);
	}
	
	@Override
	protected void onUpdated(float elapsedTime) {
        for(EntitySet set : sets)
        	for (Entity e : set){
        		CapacityActivation activation = e.get(CapacityActivation.class);
        		boolean activated = ModelManager.command.capacities.contains(activation.getFlag());
        		setComp(e, new CapacityActivation(activation.getFlag(), activated));
        	}
	}
}
