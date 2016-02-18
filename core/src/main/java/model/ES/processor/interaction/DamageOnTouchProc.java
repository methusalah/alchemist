package model.ES.processor.interaction;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.ECS.Processor;
import model.ECS.Naming;
import model.ES.component.combat.damage.DamageCapacity;
import model.ES.component.combat.damage.DamageOnTouch;
import model.ES.component.combat.damage.Damaging;
import model.ES.component.motion.Touching;

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
