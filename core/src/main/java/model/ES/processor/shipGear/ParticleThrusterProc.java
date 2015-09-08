package model.ES.processor.shipGear;

import util.LogUtil;
import model.ES.component.shipGear.RotationThruster;
import model.ES.component.visuals.ParticleCaster;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class ParticleThrusterProc extends Processor {

	@Override
	protected void registerSets() {
		register(ParticleCaster.class, RotationThruster.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime) {
	
	}
	
	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime) {
		ParticleCaster caster = e.get(ParticleCaster.class);
		RotationThruster thruster = e.get(RotationThruster.class);
		
		caster.actualPerSecond = (int)Math.round(caster.getPerSecond()*thruster.getActivation());
		if(caster.actualPerSecond > 0)
			LogUtil.info("activating"+caster.actualPerSecond);
		setComp(e, caster);
	}
}
