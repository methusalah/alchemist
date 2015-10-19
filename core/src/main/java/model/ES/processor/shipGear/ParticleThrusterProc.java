package model.ES.processor.shipGear;

import model.ES.component.assets.RotationThruster;
import model.ES.component.assets.Thruster;
import model.ES.component.visuals.ParticleCasting;
import util.LogUtil;

import com.simsilica.es.Entity;

import controller.ECS.Processor;

public class ParticleThrusterProc extends Processor {

	@Override
	protected void registerSets() {
		register("rotation", ParticleCasting.class, RotationThruster.class);
		register("standard", ParticleCasting.class, Thruster.class);
	}
	
	@Override
	protected void onEntityUpdated(Entity e) {
		ParticleCasting casting = e.get(ParticleCasting.class);
		RotationThruster rotationThruster = e.get(RotationThruster.class);
		Thruster thruster = e.get(Thruster.class);
		
		double activationRate;
		if(rotationThruster != null)
			activationRate = rotationThruster.activation.getValue();
		else if(thruster != null)
			activationRate = thruster.activation.getValue();
		else
			throw new RuntimeException("thruster missing");
		
		setComp(e, new ParticleCasting(casting.caster, (int)Math.round(casting.caster.perSecond*activationRate)));
	}
}
