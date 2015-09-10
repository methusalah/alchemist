package model.ES.processor.shipGear;

import model.ModelManager;
import model.ES.component.planarMotion.PlanarNeededRotation;
import model.ES.component.planarMotion.PlanarNeededVelocity;
import model.ES.component.relation.BoneHolding;
import model.ES.component.relation.PlanarHolding;
import model.ES.component.shipGear.RotationThruster;
import util.LogUtil;
import util.math.AngleUtil;
import util.math.PrecisionUtil;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import controller.entityAppState.Processor;

public class RotationThrusterProc extends Processor {

	@Override
	protected void registerSets() {
		register(BoneHolding.class, RotationThruster.class);
		register(PlanarHolding.class, RotationThruster.class);
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

		EntityId holder;
		if(e.get(BoneHolding.class) != null)
			holder = e.get(BoneHolding.class).getHolder();
		else if(e.get(PlanarHolding.class) != null)
			holder = e.get(PlanarHolding.class).getHolder();
		else
			throw new RuntimeException("Missing holder");
		
		
		PlanarNeededRotation rotation = entityData.getComponent(holder, PlanarNeededRotation.class);
		double activationRate = 0;
		if(rotation != null){
			if(rotation.getAngle() > 0 && !thruster.isClockwise() 
					|| rotation.getAngle() < 0 && thruster.isClockwise()){
				activationRate = 1;
				if(!thruster.isOnOff() && Math.abs(rotation.getAngle()) < thruster.getMaxAngle())
					activationRate = Math.abs(rotation.getAngle())/thruster.getMaxAngle();
			}
		}
		setComp(e, new RotationThruster(thruster.isClockwise(), thruster.getMaxAngle(), activationRate, thruster.isOnOff()));

			
	}

}
