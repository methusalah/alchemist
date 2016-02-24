package ECS.processor.logic.interaction.damage;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import ECS.component.combat.damage.DamageCapacity;
import ECS.component.combat.damage.DamageOnTouch;
import ECS.component.combat.damage.DamageOverTime;
import ECS.component.combat.damage.Damaging;
import ECS.component.lifeCycle.LifeTime;
import ECS.component.motion.Touching;
import model.ECS.builtInComponent.Naming;
import model.ECS.pipeline.Processor;
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
