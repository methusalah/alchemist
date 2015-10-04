package model.ES.processor.ability;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.entityAppState.Processor;
import model.ES.component.Cooldown;
import model.ES.component.LifeTime;
import model.ES.component.interaction.DamageOnTouch;
import model.ES.component.interaction.DestroyedOnTouch;
import model.ES.component.interaction.EffectOnTouch;
import model.ES.component.interaction.ShockwaveOnTouch;
import model.ES.component.motion.MotionCapacity;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.PlanarVelocityToApply;
import model.ES.component.motion.physic.Physic;
import model.ES.component.relation.Parenting;
import model.ES.component.shipGear.Projectile;
import model.ES.component.shipGear.ProjectileLauncher;
import model.ES.component.shipGear.Trigger;
import model.ES.component.visuals.Model;
import model.ES.richData.CollisionShape;
import model.ES.richData.Damage;
import model.ES.richData.PhysicStat;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import util.math.AngleUtil;
import util.math.RandomUtil;

public class ProjectileLauncherProc extends Processor {

	@Override
	protected void registerSets() {
		register(PlanarStance.class, ProjectileLauncher.class, Trigger.class, Cooldown.class, Parenting.class);
	}
	
	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime) {
		manage(e, elapsedTime);
	}

	@Override
	protected void onEntityAdded(Entity e, float elapsedTime) {
		manage(e, elapsedTime);
	}
	
	private void manage(Entity e, float elapsedTime) {
		Trigger trigger = e.get(Trigger.class);
		Parenting p = e.get(Parenting.class);
		ProjectileLauncher launcher = e.get(ProjectileLauncher.class);
		if(trigger.triggered){
			PlanarStance stance = e.get(PlanarStance.class);
			EntityId firing = entityData.createEntity();
			double orientation = stance.orientation + RandomUtil.between(-launcher.precision/2, launcher.precision/2);
			entityData.setComponent(firing, new PlanarStance(stance.coord.getTranslation(stance.orientation, 0.2), orientation, stance.elevation, Point3D.UNIT_Z));
			entityData.setComponent(firing, new MotionCapacity(7, 0, 1, 0, 0));
			entityData.setComponent(firing, new PlanarVelocityToApply(Point2D.UNIT_X.getRotation(orientation)));
			entityData.setComponent(firing, new Model("human/hmissileT1/hmissileT1_02.mesh.xml", 0.0025, 0, AngleUtil.toRadians(-90), 0));
			entityData.setComponent(firing, new Physic(Point2D.ORIGIN, new PhysicStat("Missile", 0.1, new CollisionShape(0.1), 0, "Missile"), p.parent));
			entityData.setComponent(firing, new DestroyedOnTouch());
			entityData.setComponent(firing, new ShockwaveOnTouch(100, 2, 10));
			entityData.setComponent(firing, new EffectOnTouch());
			entityData.setComponent(firing, new DamageOnTouch(new Damage(1)));
			entityData.setComponent(firing, new LifeTime(System.currentTimeMillis(), 1000));
			entityData.setComponent(firing, new Projectile(p.parent, stance.coord));
			
			Cooldown cd = e.get(Cooldown.class);
			setComp(e, new Cooldown(System.currentTimeMillis(), cd.duration));
		}
	}
}
