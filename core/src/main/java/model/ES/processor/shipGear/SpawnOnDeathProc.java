package model.ES.processor.shipGear;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.ECS.Processor;
import model.ES.component.combat.resistance.Attrition;
import model.ES.component.event.SpawnOnDeath;
import model.ES.component.lifeCycle.LifeTime;
import model.ES.component.lifeCycle.ToRemove;
import model.ES.component.motion.PlanarStance;
import model.ES.serial.BlueprintLibrary;
import model.ES.serial.PrototypeCreator;

/**
 * Must be called before ToRemove processor
 * @author Benoît
 *
 */
public class SpawnOnDeathProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(SpawnOnDeath.class, ToRemove.class);
	}
	
	@Override
	protected void onEntityUpdated(Entity e) {
		SpawnOnDeath spawn = e.get(SpawnOnDeath.class);

		EntityId spawned = BlueprintLibrary.getBlueprint(spawn.getBlueprint()).createEntity(entityData, null);
		
		PlanarStance eStance = entityData.getComponent(e.getId(), PlanarStance.class);
		if(eStance != null && entityData.getComponent(spawned, PlanarStance.class) != null)
			entityData.setComponent(spawned, eStance);
	}
}
