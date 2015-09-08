package model.ES.processor.shipGear;

import model.ES.component.planarMotion.PlanarThrust;
import model.ES.component.relation.BoneHolding;
import model.ES.component.relation.PlanarHolding;
import model.ES.component.shipGear.RotationThruster;
import model.ES.component.shipGear.Thruster;
import util.math.AngleUtil;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.entityAppState.Processor;

public class ThrusterProc extends Processor {

	@Override
	protected void registerSets() {
		register(BoneHolding.class, Thruster.class);
		register(PlanarHolding.class, RotationThruster.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime) {
	
	}
	
	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime) {
		Thruster thruster = e.get(Thruster.class);
		
		EntityId holder;
		if(e.get(BoneHolding.class) != null)
			holder = e.get(BoneHolding.class).getHolder();
		else if(e.get(PlanarHolding.class) != null)
			holder = e.get(PlanarHolding.class).getHolder();
		else
			throw new RuntimeException("Missing holder");

		PlanarThrust thrust = entityData.getComponent(holder, PlanarThrust.class);
		if(thrust != null){
			double diff = AngleUtil.getSmallestDifference(thrust.getDirection().getAngle(), thruster.getDirection().get2D().getAngle());
			if(diff <= thruster.getActivationAngle()){
				double activationRate = 1;
				if(!thruster.isOnOff())
					activationRate = 1-(diff/thruster.getActivationAngle());
				setComp(e, new Thruster(thruster.getDirection(), thruster.getActivationAngle(), activationRate, thruster.isOnOff()));
			}
		}
	}

}
