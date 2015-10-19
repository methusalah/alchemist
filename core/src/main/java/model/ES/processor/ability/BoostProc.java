package model.ES.processor.ability;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.ECS.Processor;
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
import model.ES.component.shipGear.Boost;
import model.ES.component.shipGear.Projectile;
import model.ES.component.shipGear.ProjectileLauncher;
import model.ES.component.shipGear.Trigger;
import model.ES.component.visuals.Model;
import model.ES.richData.CollisionShape;
import model.ES.richData.Damage;
import model.ES.richData.PhysicStat;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import util.math.Angle;
import util.math.AngleUtil;
import util.math.Fraction;
import util.math.RandomUtil;

public class BoostProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(Boost.class, Trigger.class, Parenting.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		Trigger trigger = e.get(Trigger.class);
		Parenting p = e.get(Parenting.class);
		Boost boost = e.get(Boost.class);
		
		if(trigger.triggered){
			PlanarStance parentStance = entityData.getComponent(p.getParent(), PlanarStance.class);
			PlanarVelocityToApply parentVelocity = entityData.getComponent(p.getParent(), PlanarVelocityToApply.class);
			if(parentStance == null || parentVelocity == null)
				return;

			LogUtil.info("boost !!!!");
			
			Point2D newVel = parentVelocity.getVector().getAddition(Point2D.ORIGIN.getTranslation(parentStance.getOrientation().getValue(), boost.getForce()));
			
			entityData.setComponent(p.getParent(), new PlanarVelocityToApply(newVel));
		}
	}
}
