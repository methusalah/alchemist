package model.ES.processor.shipGear;

import model.ES.component.shipGear.RotationThruster;
import model.ES.component.shipGear.Thruster;
import model.ES.component.visuals.ParticleCaster;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class ParticleThrusterProc extends Processor {

	@Override
	protected void registerSets() {
		register(ParticleCaster.class, RotationThruster.class);
		register(ParticleCaster.class, Thruster.class);
	}
	
	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime) {
		ParticleCaster caster = e.get(ParticleCaster.class);
		RotationThruster rotationThruster = e.get(RotationThruster.class);
		Thruster thruster = e.get(Thruster.class);
		
		double activationRate;
		if(rotationThruster != null)
			activationRate = rotationThruster.activation;
		else if(thruster != null)
			activationRate = thruster.activation;
		else
			throw new RuntimeException("thruster missing");
		
		
		caster.actualPerSecond = (int)Math.round(caster.perSecond*activationRate);
		setComp(e, caster);
	}
}
