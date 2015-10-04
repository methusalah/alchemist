package model.ES.processor.shipGear;

import model.ModelManager;
import model.ES.component.command.PlanarNeededRotation;
import model.ES.component.motion.PlanarVelocityToApply;
import model.ES.component.relation.BoneHolding;
import model.ES.component.relation.Parenting;
import model.ES.component.relation.PlanarHolding;
import model.ES.component.shipGear.RotationThruster;
import util.LogUtil;
import util.math.AngleUtil;
import util.math.PrecisionUtil;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import controller.entityAppState.Processor;
import javafx.scene.Parent;

public class RotationThrusterProc extends Processor {

	@Override
	protected void registerSets() {
		register(RotationThruster.class, Parenting.class);
	}
	
	@Override
	protected void onUpdated(float elapsedTime) {
		if(ModelManager.command.target == null)
			return;
		
        for(EntitySet set : sets)
        	for (Entity e : set){
        		manage(e, elapsedTime);
        	}
	}
	
	private void manage(Entity e, float elapsedTime) {
		RotationThruster thruster = e.get(RotationThruster.class);
		Parenting parenting = e.get(Parenting.class);
		EntityId holder = parenting.parent;
		
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
