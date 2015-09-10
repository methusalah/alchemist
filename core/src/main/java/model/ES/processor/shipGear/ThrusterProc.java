package model.ES.processor.shipGear;

import model.ModelManager;
import model.ES.component.planarMotion.PlanarNeededVelocity;
import model.ES.component.relation.BoneHolding;
import model.ES.component.relation.PlanarHolding;
import model.ES.component.shipGear.RotationThruster;
import model.ES.component.shipGear.Thruster;
import util.LogUtil;
import util.math.AngleUtil;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import controller.entityAppState.Processor;

public class ThrusterProc extends Processor {

	@Override
	protected void registerSets() {
		register(BoneHolding.class, Thruster.class);
		register(PlanarHolding.class, Thruster.class);
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
		Thruster thruster = e.get(Thruster.class);
		
		EntityId holder;
		if(e.get(BoneHolding.class) != null)
			holder = e.get(BoneHolding.class).getHolder();
		else if(e.get(PlanarHolding.class) != null)
			holder = e.get(PlanarHolding.class).getHolder();
		else
			throw new RuntimeException("Missing holder");

		PlanarNeededVelocity thrust = entityData.getComponent(holder, PlanarNeededVelocity.class);
		double activationRate = 0;
		if(thrust != null){
			double diff = AngleUtil.getSmallestDifference(thrust.getDirection().getAngle(), thruster.getDirection().get2D().getAngle());
			if(diff <= thruster.getActivationAngle()){
				activationRate = 1;
				if(!thruster.isOnOff())
					activationRate = 1-(diff/thruster.getActivationAngle());
			}
		}
		setComp(e, new Thruster(thruster.getDirection(), thruster.getActivationAngle(), activationRate, thruster.isOnOff()));
	}

}
