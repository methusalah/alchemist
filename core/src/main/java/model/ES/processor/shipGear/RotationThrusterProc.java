package model.ES.processor.shipGear;

import model.ES.component.assets.RotationThruster;
import model.ES.component.command.PlanarNeededRotation;
import model.ES.component.hierarchy.Parenting;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import controller.ECS.Processor;

public class RotationThrusterProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(RotationThruster.class, Parenting.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		RotationThruster thruster = e.get(RotationThruster.class);
		Parenting parenting = e.get(Parenting.class);
		EntityId holder = parenting.getParent();
		
		PlanarNeededRotation rotation = entityData.getComponent(holder, PlanarNeededRotation.class);
		double activationRate = 0;
		if(rotation != null){
			if(rotation.angle.getValue() > 0 && !thruster.clockwise 
					|| rotation.angle.getValue() < 0 && thruster.clockwise){
				activationRate = 1;
				if(!thruster.onOff && Math.abs(rotation.angle.getValue()) < thruster.maxAngle)
					activationRate = Math.abs(rotation.angle.getValue())/thruster.maxAngle;
			}
		}
		setComp(e, new RotationThruster(thruster.clockwise, thruster.maxAngle, activationRate, thruster.onOff));

			
	}

}
