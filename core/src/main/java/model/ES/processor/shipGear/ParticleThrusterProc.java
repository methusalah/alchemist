package model.ES.processor.shipGear;

import model.ES.component.shipGear.RotationThruster;
import model.ES.component.shipGear.Thruster;
import model.ES.component.visuals.ParticleCasting;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class ParticleThrusterProc extends Processor {

	@Override
	protected void registerSets() {
		register(ParticleCasting.class, RotationThruster.class);
		register(ParticleCasting.class, Thruster.class);
	}
	
	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime) {
		ParticleCasting casting = e.get(ParticleCasting.class);
		RotationThruster rotationThruster = e.get(RotationThruster.class);
		Thruster thruster = e.get(Thruster.class);
		
		double activationRate;
		if(rotationThruster != null)
			activationRate = rotationThruster.activation;
		else if(thruster != null)
			activationRate = thruster.activation;
		else
			throw new RuntimeException("thruster missing");
		
		
		setComp(e, new ParticleCasting(casting.caster, (int)Math.round(casting.caster.perSecond*activationRate)));
	}
}
