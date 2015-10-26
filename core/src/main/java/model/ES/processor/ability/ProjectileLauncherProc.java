package model.ES.processor.ability;

import java.util.ArrayList;
import java.util.List;

import com.jme3.util.blockparser.BlockLanguageParser;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.ECS.Processor;
import model.ES.component.Cooldown;
import model.ES.component.LifeTime;
import model.ES.component.Naming;
import model.ES.component.assets.Ability;
import model.ES.component.assets.Projectile;
import model.ES.component.assets.ProjectileLauncher;
import model.ES.component.hierarchy.Parenting;
import model.ES.component.interaction.DamageOnTouch;
import model.ES.component.interaction.DestroyedOnTouch;
import model.ES.component.interaction.EffectOnTouch;
import model.ES.component.interaction.ShockwaveOnTouch;
import model.ES.component.motion.MotionCapacity;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.PlanarVelocityToApply;
import model.ES.component.motion.physic.CircleCollisionShape;
import model.ES.component.motion.physic.Physic;
import model.ES.component.visuals.Model;
import model.ES.richData.Damage;
import model.ES.serial.Blueprint;
import model.ES.serial.BlueprintLibrary;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import util.math.Angle;
import util.math.AngleUtil;
import util.math.Fraction;
import util.math.RandomUtil;

public class ProjectileLauncherProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(PlanarStance.class, ProjectileLauncher.class, Ability.class, Cooldown.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		Ability trigger = e.get(Ability.class);
		if(trigger.isTriggered()){
			ProjectileLauncher launcher = e.get(ProjectileLauncher.class);
			if(!launcher.getProjectileBluePrint().isEmpty()){
				PlanarStance stance = e.get(PlanarStance.class);
				double orientation = stance.orientation.getValue() + ((RandomUtil.next()-0.5)*(1-launcher.getPrecision().getValue()))*AngleUtil.FULL;
				
				EntityId eid = BlueprintLibrary.getBlueprint(launcher.getProjectileBluePrint()).createEntity(entityData, null);
				entityData.setComponent(eid, new PlanarStance(stance.coord.getTranslation(stance.orientation.getValue(), 0.2), new Angle(orientation), stance.elevation, Point3D.UNIT_Z));
				// TODO manage spawning exception
				// TODO manage attacker exception
				
				
			} else {
				PlanarStance stance = e.get(PlanarStance.class);
				
				EntityId firing = entityData.createEntity();
				entityData.setComponent(firing, new Naming("projectile"));
				double orientation = stance.orientation.getValue() + ((RandomUtil.next()-0.5)*(1-launcher.getPrecision().getValue()))*AngleUtil.FULL;
				entityData.setComponent(firing, new PlanarStance(stance.coord.getTranslation(stance.orientation.getValue(), 0.2), new Angle(orientation), stance.elevation, Point3D.UNIT_Z));
				entityData.setComponent(firing, new MotionCapacity(0, 1, 0, 0));
				
				// application of the velocity of the parent to the projectile
				entityData.setComponent(firing, new PlanarVelocityToApply(new Point2D(1.5, 0).getRotation(orientation)));
				EntityId p = entityData.getComponent(e.getId(), Parenting.class).getParent();
				
				
				entityData.setComponent(firing, new Model("human/hmissileT1/hmissileT1_02.mesh.xml", 0.0025, new Angle(0), new Angle(AngleUtil.toRadians(-90)), new Angle(0)));
				List<String> exceptions = new ArrayList<>();
				exceptions.add("Missile");
				entityData.setComponent(firing, new Physic(Point2D.ORIGIN, "Missile", exceptions, 0.1, new Fraction(0), p));
				entityData.setComponent(firing, new CircleCollisionShape(0.1));
				entityData.setComponent(firing, new DestroyedOnTouch());
				entityData.setComponent(firing, new ShockwaveOnTouch(100, 4, 20));
				entityData.setComponent(firing, new EffectOnTouch());
				entityData.setComponent(firing, new DamageOnTouch(new Damage(1)));
				entityData.setComponent(firing, new LifeTime(System.currentTimeMillis(), 4000));
				// TODO locate aggressor better in the hierarchy
				entityData.setComponent(firing, new Projectile(p, stance.coord));
				
				Cooldown cd = e.get(Cooldown.class);
				setComp(e, new Cooldown(System.currentTimeMillis(), cd.duration));
			}
		}
	}
}
