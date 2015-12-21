package model.ES.processor.ability;

import com.simsilica.es.Entity;

import controller.ECS.Processor;
import model.ES.component.assets.Ability;
import model.ES.component.assets.Boost;
import model.ES.component.hierarchy.Parenting;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.PlanarVelocityToApply;
import model.ES.component.motion.physic.Physic;
import util.LogUtil;
import util.geometry.geom2d.Point2D;

public class BoostProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(Boost.class, Ability.class, Parenting.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		Ability ability = e.get(Ability.class);
		Parenting p = e.get(Parenting.class);
		Boost boost = e.get(Boost.class);
		
		if(ability.isTriggered()){
			PlanarStance parentStance = entityData.getComponent(p.getParent(), PlanarStance.class);
			PlanarVelocityToApply parentVelocity = entityData.getComponent(p.getParent(), PlanarVelocityToApply.class);
			Physic parentPhysic = entityData.getComponent(p.getParent(), Physic.class);
			if(parentStance == null || parentVelocity == null)
				return;
			
			Point2D newVel = parentVelocity.getVector().getAddition(Point2D.ORIGIN.getTranslation(parentStance.getOrientation().getValue(), boost.getForce()));
			
			entityData.setComponent(p.getParent(), new PlanarVelocityToApply(newVel));
			
			// to stop entity movement
			entityData.setComponent(p.getParent(), new Physic(parentPhysic.getVelocity().getDivision(10), parentPhysic.getType(), parentPhysic.getExceptions(), parentPhysic.getMass(), parentPhysic.getRestitution(), parentPhysic.getSpawnerException()));
		}
	}
}
