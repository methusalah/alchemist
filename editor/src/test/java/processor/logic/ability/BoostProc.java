package test.java.processor.logic.ability;

import com.simsilica.es.Entity;

import main.java.model.ECS.pipeline.Processor;
import model.ECS.Parenting;
import model.ES.component.ability.Ability;
import model.ES.component.motion.Boost;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.PlanarVelocityToApply;
import model.ES.component.motion.physic.Physic;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;

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
			
			Point2D newVel;
			// to enhance the power feeling, we double the force while it's velocity is not aligned in the needed direction 
			if(AngleUtil.getSmallestDifference(parentPhysic.getVelocity().getAngle(), parentStance.getOrientation().getValue()) > AngleUtil.toRadians(10))
				newVel = parentVelocity.getVector().getAddition(Point2D.ORIGIN.getTranslation(parentStance.getOrientation().getValue(), boost.getForce()*2));
			else
				newVel = parentVelocity.getVector().getAddition(Point2D.ORIGIN.getTranslation(parentStance.getOrientation().getValue(), boost.getForce()));
			
			entityData.setComponent(p.getParent(), new PlanarVelocityToApply(newVel));
		}
	}
}
