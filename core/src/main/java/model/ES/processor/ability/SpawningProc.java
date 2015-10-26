package model.ES.processor.ability;

import model.ES.component.assets.Spawning;
import model.ES.component.motion.PlanarStance;
import model.ES.serial.PrototypeCreator;
import util.math.RandomUtil;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.ECS.Processor;

public class SpawningProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(Spawning.class, PlanarStance.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		PlanarStance stance = e.get(PlanarStance.class);
		Spawning s = e.get(Spawning.class);
		
		int nb = s.getToSpawn()+RandomUtil.between(0, s.getToSpawnRange());
		
		for(int i = 0; i<nb; i++){
			EntityId spawned = PrototypeCreator.create(s.getBlueprintName(), null);
			if(entityData.getComponent(spawned, PlanarStance.class) != null)
				entityData.setComponent(spawned, stance);
		}
	}
}
