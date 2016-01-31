package model.ES.processor.interaction;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.ECS.Processor;
import model.ES.component.LifeTime;
import model.ES.component.Naming;
import model.ES.component.ToRemove;
import model.ES.component.assets.DamageCapacity;
import model.ES.component.interaction.DamageOnTouch;
import model.ES.component.interaction.Damaging;
import model.ES.component.interaction.EffectOnTouch;
import model.ES.component.interaction.senses.Touching;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.physic.Collisioning;
import util.LogUtil;
import util.geometry.geom3d.Point3D;

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
