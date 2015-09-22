package model.ES.processor.shipGear;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;
import model.ES.component.shipGear.Thruster;
import model.ES.component.visuals.Lighting;

public class LightThrusterProc extends Processor {

	@Override
	protected void registerSets() {
		register(Lighting.class, Thruster.class);
	}
	
	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime) {
		Lighting l = e.get(Lighting.class);
		Thruster thruster = e.get(Thruster.class);
		
		setComp(e, new Lighting(l.color, l.intensity, l.distance, l.innerAngle, l.outerAngle, l.shadowCaster, thruster.getActivation()));
	}
}
