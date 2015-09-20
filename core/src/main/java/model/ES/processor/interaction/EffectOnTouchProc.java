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

public class EffectOnTouchProc extends Processor {

	@Override
	protected void registerSets() {
		register(EffectOnTouch.class, Touching.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime) {
		EntityId eid = entityData.createEntity();
		entityData.setComponent(eid, getCaster1());
		entityData.setComponent(eid, new PlanarStance(e.get(Touching.class).getCoord(), 0, 0.5, Point3D.UNIT_Z));
		entityData.setComponent(eid, new LifeTime(System.currentTimeMillis(), 100));
	}

	
	private ParticleCaster getCaster1(){
		return new ParticleCaster("particles/flame.png",
				2,
				2,
				0,
				0,
				false,
				300,
				100,
				0.2,
				0.1,
				new Color(1, 0.3f, 0.3f, 1),
				new Color(0.5f, 0.5f, 0.5f, 1),
				0.1,
				0.2,
				0,
				false,
				ParticleCaster.Facing.Camera,
				true,
				0,
				false);
	}

}
