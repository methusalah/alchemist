package model.ES.processor.shipGear;

import com.simsilica.es.Entity;

import controller.ECS.Processor;
import model.ES.component.assets.Thruster;
import model.ES.component.visuals.Lighting;

public class LightThrusterProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(Lighting.class, Thruster.class);
	}
	
	@Override
	protected void onEntityUpdated(Entity e) {
		Lighting l = e.get(Lighting.class);
		Thruster thruster = e.get(Thruster.class);
		
		setComp(e, new Lighting(l.color, l.intensity, l.distance, l.innerAngle, l.outerAngle, l.shadowIntensity, thruster.activation));
	}
}
