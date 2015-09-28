package model.ES.processor.interaction;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.entityAppState.Processor;
import model.ES.component.LifeTime;
import model.ES.component.interaction.DamageOnTouch;
import model.ES.component.interaction.Damaging;
import model.ES.component.interaction.EffectOnTouch;
import model.ES.component.interaction.senses.Touching;
import model.ES.component.motion.PlanarStance;
import util.geometry.geom3d.Point3D;

public class DamageOnTouchProc extends Processor {

	@Override
	protected void registerSets() {
		register(DamageOnTouch.class, Touching.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime) {
		DamageOnTouch dmg = e.get(DamageOnTouch.class);
		Touching touching = e.get(Touching.class);
		EntityId eid = entityData.createEntity();
		entityData.setComponent(eid, new Damaging(e.getId(), touching.getTouched(), dmg.damage));
	}
}
