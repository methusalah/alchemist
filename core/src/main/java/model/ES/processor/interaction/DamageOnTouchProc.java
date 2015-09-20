package model.ES.processor.interaction;

import java.awt.Color;

import model.ES.component.LifeTime;
import model.ES.component.interaction.EffectOnTouch;
import model.ES.component.interaction.Touching;
import model.ES.component.motion.PlanarStance;
import model.ES.component.visuals.ParticleCaster;
import util.geometry.geom3d.Point3D;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.entityAppState.Processor;

public class DamageOnTouchProc extends Processor {

	@Override
	protected void registerSets() {
		register(EffectOnTouch.class, Touching.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime) {
		EntityId eid = entityData.createEntity();
//		entityData.setComponent(eid, getCaster1());
		entityData.setComponent(eid, new PlanarStance(e.get(Touching.class).getCoord(), 0, 0.5, Point3D.UNIT_Z));
		entityData.setComponent(eid, new LifeTime(System.currentTimeMillis(), 100));
	}
}
