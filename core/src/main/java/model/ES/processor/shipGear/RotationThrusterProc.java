package model.ES.processor.shipGear;

import model.ES.component.command.PlanarNeededRotation;
import model.ES.component.relation.Parenting;
import model.ES.component.shipGear.RotationThruster;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import controller.entityAppState.Processor;

public class RotationThrusterProc extends Processor {

	@Override
	protected void registerSets() {
		register(RotationThruster.class, Parenting.class);
	}
	
	@Override
	protected void onUpdated(float elapsedTime) {
        for(EntitySet set : sets)
        	for (Entity e : set){
        		manage(e, elapsedTime);
        	}
	}
	
	private void manage(Entity e, float elapsedTime) {
		RotationThruster thruster = e.get(RotationThruster.class);
		Parenting parenting = e.get(Parenting.class);
		EntityId holder = parenting.getParent();
		
		PlanarNeededRotation rotation = entityData.getComponent(holder, PlanarNeededRotation.class);
		double activationRate = 0;
		if(rotation != null){
			if(rotation.angle > 0 && !thruster.clockwise 
					|| rotation.angle < 0 && thruster.clockwise){
				activationRate = 1;
				if(!thruster.onOff && Math.abs(rotation.angle) < thruster.maxAngle)
					activationRate = Math.abs(rotation.angle)/thruster.maxAngle;
			}
		}
		setComp(e, new RotationThruster(thruster.clockwise, thruster.maxAngle, activationRate, thruster.onOff));

			
	}

}
