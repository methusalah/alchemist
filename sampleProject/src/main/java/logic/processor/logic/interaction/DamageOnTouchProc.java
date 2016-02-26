package logic.processor.logic.interaction;

import com.brainless.alchemist.model.ECS.builtInComponent.Naming;
import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import component.combat.damage.DamageCapacity;
import component.combat.damage.DamageOnTouch;
import component.combat.damage.Damaging;
import component.motion.Touching;

public class DamageOnTouchProc extends BaseProcessor {

	@Override
	protected void registerSets() {
		registerDefault(DamageCapacity.class, DamageOnTouch.class, Touching.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		EntityId eid = entityData.createEntity();
		entityData.setComponent(eid, new Naming("damaging"));
		entityData.setComponent(eid, new Damaging(e.getId(), e.get(Touching.class).getTouched()));
		entityData.setComponent(eid, e.get(DamageCapacity.class));
		
	}
}
