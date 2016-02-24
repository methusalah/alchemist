package ECS.processor.logic.interaction;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import ECS.component.combat.damage.DamageCapacity;
import ECS.component.combat.damage.DamageOnTouch;
import ECS.component.combat.damage.Damaging;
import ECS.component.motion.Touching;
import model.ECS.builtInComponent.Naming;
import model.ECS.pipeline.Processor;

public class DamageOnTouchProc extends Processor {

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
