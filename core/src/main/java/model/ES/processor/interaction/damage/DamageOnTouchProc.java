package model.ES.processor.interaction.damage;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.ECS.Processor;
import model.ECS.Naming;
import model.ES.component.combat.damage.DamageCapacity;
import model.ES.component.combat.damage.DamageOnTouch;
import model.ES.component.combat.damage.DamageOverTime;
import model.ES.component.combat.damage.Damaging;
import model.ES.component.lifeCycle.LifeTime;
import model.ES.component.motion.Touching;
import util.math.RandomUtil;

public class DamageOnTouchProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(DamageCapacity.class, DamageOnTouch.class, Touching.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		DamageCapacity capacity = e.get(DamageCapacity.class);
		
		// creating a standard damage
		EntityId eid = entityData.createEntity();
		entityData.setComponent(eid, new Naming("damaging"));
		entityData.setComponent(eid, new Damaging(e.getId(), e.get(Touching.class).getTouched()));
		entityData.setComponent(eid, capacity);
		
		// creating an optional damage over time
		if(RandomUtil.next() < capacity.getDotChance().getValue()){
			EntityId dotId = entityData.createEntity();
			entityData.setComponent(dotId, new Naming("dot damaging"));
			entityData.setComponent(dotId, new Damaging(e.getId(), e.get(Touching.class).getTouched()));
			entityData.setComponent(dotId, new DamageOverTime(capacity.getType(),
					capacity.getDotPerSecond(),
					3,
					0));
			entityData.setComponent(dotId, new LifeTime(10000));
		}
		
		
	}
}
