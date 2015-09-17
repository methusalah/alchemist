package model.ES.processor.shipGear;

import model.ES.component.Cooldown;
import model.ES.component.LifeTime;
import model.ES.component.physic.Physic;
import model.ES.component.physic.collision.CollisionShape;
import model.ES.component.physic.collision.DestroyedOnTouch;
import model.ES.component.physic.collision.EffectOnTouch;
import model.ES.component.planarMotion.PlanarMotionCapacity;
import model.ES.component.planarMotion.PlanarNeededVelocity;
import model.ES.component.planarMotion.PlanarStance;
import model.ES.component.planarMotion.PlanarWipping;
import model.ES.component.shipGear.CapacityActivation;
import model.ES.component.shipGear.Gun;
import model.ES.component.visuals.Model;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import util.math.AngleUtil;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.entityAppState.Processor;

public class GunProc extends Processor {

	@Override
	protected void registerSets() {
		register(PlanarStance.class, Gun.class, Cooldown.class, CapacityActivation.class);
	}
	
	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime) {
		CapacityActivation activation = e.get(CapacityActivation.class);
		if(!activation.isActivated())
			return;
		
		Cooldown cd = e.get(Cooldown.class);
		if(System.currentTimeMillis()-cd.getStart() > cd.getDuration()){
			PlanarStance stance = e.get(PlanarStance.class);
			setComp(e, new Cooldown(System.currentTimeMillis(), cd.getDuration()));
			
			EntityId firing = entityData.createEntity();
			entityData.setComponent(firing, new PlanarStance(stance.getCoord().getTranslation(stance.getOrientation(), 1), stance.getOrientation(), stance.getElevation(), Point3D.UNIT_Z));
			entityData.setComponent(firing, new PlanarWipping(Point2D.ORIGIN, 0));
			entityData.setComponent(firing, new PlanarMotionCapacity(8, 0, 100, 1));
			entityData.setComponent(firing, new PlanarNeededVelocity(Point2D.UNIT_X));
			entityData.setComponent(firing, new Model("human/hmissileT1/hmissileT1_02.mesh.xml", 0.0025, 0, AngleUtil.toRadians(-90), 0));
			entityData.setComponent(firing, new Physic(new CollisionShape(0.1), 0));
			entityData.setComponent(firing, new DestroyedOnTouch());
			entityData.setComponent(firing, new EffectOnTouch());
			entityData.setComponent(firing, new LifeTime(System.currentTimeMillis(), 1000));
		}

	}
}
