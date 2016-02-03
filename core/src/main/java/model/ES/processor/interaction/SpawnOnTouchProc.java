package model.ES.processor.interaction;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.ECS.Processor;
import model.ES.component.LifeTime;
import model.ES.component.Naming;
import model.ES.component.interaction.SpawnOnTouch;
import model.ES.component.interaction.senses.Touching;
import model.ES.component.motion.PlanarStance;
import model.ES.component.visuals.ParticleCaster;
import model.ES.richData.ColorData;
import util.geometry.geom3d.Point3D;
import util.math.Angle;
import util.math.Fraction;

public class SpawnOnTouchProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(SpawnOnTouch.class, Touching.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		EntityId eid = entityData.createEntity();
		entityData.setComponent(eid, new Naming("particle"));
		entityData.setComponent(eid, getCaster1());
		entityData.setComponent(eid, new PlanarStance(e.get(Touching.class).getCoord(), new Angle(0), 0.5, Point3D.UNIT_Z));
		entityData.setComponent(eid, new LifeTime(100));
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
				new ColorData(1, 0.3f, 0.3f, 1),
				new ColorData(0.5f, 0.5f, 0.5f, 1),
				0.1,
				0.2,
				0,
				false,
				ParticleCaster.Facing.Camera,
				true,
				0,
				false,
				new Fraction(1));
	}

}
