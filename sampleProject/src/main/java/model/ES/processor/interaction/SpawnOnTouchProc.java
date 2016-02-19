package model.ES.processor.interaction;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import main.java.model.ECS.pipeline.Processor;
import model.ECS.Naming;
import model.ES.component.assets.ParticleCaster;
import model.ES.component.lifeCycle.LifeTime;
import model.ES.component.lifeCycle.SpawnOnTouch;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.Touching;
import model.tempImport.BlueprintLibrary;
import model.tempImport.ColorData;
import util.geometry.geom3d.Point3D;
import util.math.Angle;
import util.math.Fraction;

public class SpawnOnTouchProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(SpawnOnTouch.class, Touching.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		SpawnOnTouch spawn = e.get(SpawnOnTouch.class);
		Touching touching = e.get(Touching.class);
		
		for(String bpName : spawn.getBlueprintNames()) {
			EntityId spawned = BlueprintLibrary.getBlueprint(bpName).createEntity(entityData, null);
			PlanarStance stance = entityData.getComponent(spawned, PlanarStance.class); 
			if(stance != null)
				entityData.setComponent(spawned, new PlanarStance(touching.getCoord(), stance.getOrientation(), stance.getElevation(), stance.getUpVector()));
		}
	}
}
