package processor.logic.shipGear;

import com.simsilica.es.Entity;

import component.assets.Lighting;
import component.motion.Thruster;
import model.ECS.pipeline.Processor;

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
