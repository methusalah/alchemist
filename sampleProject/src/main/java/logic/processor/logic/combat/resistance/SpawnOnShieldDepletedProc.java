package logic.processor.logic.combat.resistance;

import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.simsilica.es.Entity;

import component.combat.resistance.Attrition;
import component.combat.resistance.SpawnOnShieldDepleted;
import logic.commonLogic.Spawner;

public class SpawnOnShieldDepletedProc extends BaseProcessor {

	@Override
	protected void registerSets() {
		registerDefault(SpawnOnShieldDepleted.class, Attrition.class);
	}
	
	@Override
	protected void onEntityUpdated(Entity e) {
		SpawnOnShieldDepleted spawn = e.get(SpawnOnShieldDepleted.class);
		Attrition att = e.get(Attrition.class);
		
		if(att.getActualShield() != spawn.getLastShieldValue()){
			if(att.getActualShield() == 0){
				Spawner.spawnWithSpawnerStance(entityData, e.getId(), spawn.getBlueprintNames());
			}
			setComp(e, new SpawnOnShieldDepleted(spawn.getBlueprintNames(), att.getActualShield()));
		}
	}
}
